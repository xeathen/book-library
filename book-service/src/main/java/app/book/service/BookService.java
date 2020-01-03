package app.book.service;

import app.ErrorCodes;
import app.author.domain.Author;
import app.book.api.book.BookView;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.BorrowedRecordView;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchRecordResponse;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Reservation;
import app.category.domain.Category;
import app.tag.domain.Tag;
import app.user.api.BOUserWebService;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.UserStatusView;
import com.mongodb.client.model.Filters;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Repository<Category> categoryRepository;
    @Inject
    Repository<Tag> tagRepository;
    @Inject
    Repository<Author> authorRepository;
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    MongoCollection<BorrowedRecord> mongoCollection;
    @Inject
    BOUserWebService boUserWebService;
    @Inject
    Database database;

    public GetBookResponse get(Long bookId) {
        Optional<Book> book = bookRepository.get(bookId);
        book.orElseThrow(() -> new NotFoundException("Book not found.", ErrorCodes.BOOK_NOT_FOUND));
        return getBookResponse(book.get());
    }

    public SearchBookResponse search(SearchBookRequest request) {
        List<String> params = new ArrayList<>();
        String selectSQL = "SELECT books.id AS id, books.name AS name, authors.name AS author_name, " +
            "categories.name AS category_name, tags.name AS tag_name, " +
            "books.publishing_house, books.description , books.mount ";
        String fromSQL = "FROM `books` JOIN `categories` JOIN `tags` JOIN `authors` " +
            "ON books.category_id = categories.id AND tags.id = books.tag_id AND `authors`.id = books.author_id ";
        String whereSQL = whereSQL(request, params);
        String limitSQL = "limit " + request.skip + ", " + request.limit;
        SearchBookResponse response = new SearchBookResponse();
        response.books = database.select(selectSQL + fromSQL + whereSQL + limitSQL, BookView.class, params.toArray());
        String countSQL = "SELECT COUNT(1) ";
        response.total = database.selectOne(countSQL + fromSQL + whereSQL, Integer.class, params.toArray()).orElse(0);
        return response;
    }

    public SearchRecordResponse searchRecordByUserId(Long userId) {
        SearchRecordResponse response = new SearchRecordResponse();
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.eq("user_id", userId);
        List<BorrowedRecord> borrowedRecordList = mongoCollection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::borrowedRecordView).collect(Collectors.toList());
        response.total = borrowedRecordList.size();
        return response;
    }

    public BorrowBookResponse borrow(BorrowBookRequest request) {
        Book book = bookRepository.get(request.bookId).orElseThrow(() ->
            new NotFoundException("Book not found.", ErrorCodes.BOOK_NOT_FOUND));
        BOGetUserResponse user = boUserWebService.get(request.userId);
        if (book.mount <= 0) {
            throw new BadRequestException("No book rest.", ErrorCodes.NO_BOOK_REST);
        }
        if (user == null) {
            throw new NotFoundException("User not found.", ErrorCodes.USER_NOT_FOUND);
        }
        if (user.status == UserStatusView.INACTIVE) {
            throw new ConflictException("You are banned!", ErrorCodes.BANNED);
        }
        if (!isReturned(request.userId, request.bookId)) {
            throw new ConflictException("You had borrowed this book already.", ErrorCodes.BORROWED_ALREADY);
        }
        if (ZonedDateTime.now().isAfter(request.returnTime)) {
            throw new BadRequestException("ReturnTime is past.", ErrorCodes.RETURN_TIME_PAST);
        }
        BorrowedRecord borrowedRecord = createBorrowedRecord(request, book, user);
        mongoCollection.insert(borrowedRecord);
        changeBookMount(book, -1);
        return borrowBookResponse(borrowedRecord);
    }

    public ReturnBookResponse returnBack(ReturnBookRequest request) {
        ReturnBookResponse response = new ReturnBookResponse();
        if (isReturned(request.userId, request.bookId)) {
            throw new NotFoundException("Record not found.", ErrorCodes.RECORD_NOT_FOUND);
        }
        List<BorrowedRecord> borrowedRecordList = getNotReturnedRecordList(request.userId, request.bookId);
        BorrowedRecord record = borrowedRecordList.get(0);
        record.returnTime = ZonedDateTime.now();
        record.isReturned = Boolean.TRUE;
        mongoCollection.replace(record);
        response.userId = request.userId;
        response.userName = record.userName;
        response.bookId = request.bookId;
        response.bookName = record.bookName;
        response.returnTime = ZonedDateTime.now();
        Book book = bookRepository.get(request.bookId).orElseThrow(() ->
            new NotFoundException("Book not found.", ErrorCodes.BOOK_NOT_FOUND));
        changeBookMount(book, 1);
        return response;
    }

    private void changeBookMount(Book book, Integer x) {
        Integer mount = book.mount;
        Book borrowedBook = new Book();
        borrowedBook.id = book.id;
        borrowedBook.mount = mount + x;
        bookRepository.partialUpdate(borrowedBook);
    }

    private BorrowedRecord createBorrowedRecord(BorrowBookRequest request, Book book, BOGetUserResponse response) {
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.id = UUID.randomUUID().toString();
        borrowedRecord.userId = request.userId;
        borrowedRecord.userName = response.userName;
        borrowedRecord.bookId = request.bookId;
        borrowedRecord.bookName = book.name;
        borrowedRecord.borrowTime = ZonedDateTime.now();
        borrowedRecord.returnTime = request.returnTime;
        borrowedRecord.isReturned = Boolean.FALSE;
        return borrowedRecord;
    }

    public CreateReservationResponse reserve(CreateReservationRequest request) {
        Query<Reservation> query = reservationRepository.select();
        query.where("user_id = ?", request.userId);
        query.where("book_id = ?", request.bookId);
        List<Reservation> collect = new ArrayList<>(query.fetch());
        if (!collect.isEmpty()) {
            throw new BadRequestException("You have reserved this book already.", ErrorCodes.ALREADY_RESERVED);
        }
        createReservation(request.userId, request.bookId);
        return createReservationResponse(request);
    }

    private void createReservation(Long userId, Long bookId) {
        Reservation reservation = new Reservation();
        reservation.userId = userId;
        reservation.bookId = bookId;
        reservation.reserveTime = ZonedDateTime.now();
        reservationRepository.insert(reservation);
    }

    private List<BookView> select(SearchBookRequest request) {
        //TODO:分页查询
        StringBuilder whereClause = new StringBuilder();
        List<String> params = new ArrayList<>();
        if (!Strings.isBlank(request.name)) {
            where("books.name like ?", Strings.format("%{}%", request.name), whereClause, params);
        }
        if (!Strings.isBlank(request.author)) {
            where("authors.name like ?", Strings.format("%{}%", request.author), whereClause, params);
        }
        if (!Strings.isBlank(request.category)) {
            where("categories.name like ?", Strings.format("%{}%", request.category), whereClause, params);
        }
        if (!Strings.isBlank(request.tag)) {
            where("tags.name like ?", Strings.format("%{}%", request.tag), whereClause, params);
        }
        if (!Strings.isBlank(request.publishingHouse)) {
            where("books.publishing_house like ?", Strings.format("%{}%", request.publishingHouse), whereClause, params);
        }
        if (!Strings.isBlank(request.description)) {
            where("books.description like ?", Strings.format("%{}%", request.description), whereClause, params);
        }
        String sql = "SELECT `books`.`id` as `id`, `books`.`name` as `name`, `authors`.`name` as `author_name`, "
            + "`categories`.`name` as `category_name`, tags.`name` as `tag_name`, books.`publishing_house`, books.`description` , books.`mount` "
            + "FROM `books` join `categories` join `tags` join `authors` "
            + "on books.category_id = categories.id and tags.id = books.tag_id and `authors`.id = books.author_id "
            + "where " + (whereClause.length() > 0 ? whereClause : "1 = 1")
            + "limit " + request.skip + ", " + request.limit;
        return database.select(sql, BookView.class, params.toArray());
    }

    private String whereSQL(SearchBookRequest request, List<String> params) {
        StringBuilder whereClause = new StringBuilder();
        if (!Strings.isBlank(request.name)) {
            where("books.name LIKE ?", Strings.format("%{}%", request.name), whereClause, params);
        }
        if (!Strings.isBlank(request.author)) {
            where("authors.name LIKE ?", Strings.format("%{}%", request.author), whereClause, params);
        }
        if (!Strings.isBlank(request.category)) {
            where("categories.name LIKE ?", Strings.format("%{}%", request.category), whereClause, params);
        }
        if (!Strings.isBlank(request.tag)) {
            where("tags.name LIKE ?", Strings.format("%{}%", request.tag), whereClause, params);
        }
        if (!Strings.isBlank(request.publishingHouse)) {
            where("books.publishing_house LIKE ?", Strings.format("%{}%", request.publishingHouse), whereClause, params);
        }
        if (!Strings.isBlank(request.description)) {
            where("books.description LIKE ?", Strings.format("%{}%", request.description), whereClause, params);
        }
        return whereClause.length() > 0 ? "WHERE " + whereClause.toString() : " ";
    }

    private void where(String condition, String param, StringBuilder whereClause, List<String> params) {
        if (Strings.isBlank(condition)) return;
        if (whereClause.length() > 0) whereClause.append(" AND ");
        whereClause.append(condition);
        params.add(param);
    }

    private Boolean isReturned(Long userId, Long bookId) {
        List<BorrowedRecord> notReturnedRecordList = getNotReturnedRecordList(userId, bookId);
        return notReturnedRecordList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    private List<BorrowedRecord> getNotReturnedRecordList(Long userId, Long bookId) {
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.and(Filters.eq("book_id", bookId),
            Filters.eq("user_id", userId),
            Filters.eq("is_returned", Boolean.FALSE));
        return mongoCollection.find(query);
    }

    private CreateReservationResponse createReservationResponse(CreateReservationRequest request) {
        CreateReservationResponse response = new CreateReservationResponse();
        response.userId = request.userId;
        response.userName = boUserWebService.get(request.userId).userName;
        response.bookId = request.bookId;
        response.bookName = bookRepository.get(request.bookId).orElseThrow().name;
        return response;
    }

    private GetBookResponse getBookResponse(Book book) {
        GetBookResponse response = new GetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.categoryName = categoryRepository.get(book.categoryId).orElseThrow(() ->
            new NotFoundException("Category not found.", ErrorCodes.CATEGORY_NOT_FOUND)).name;
        response.tagName = tagRepository.get(book.tagId).orElseThrow(() ->
            new NotFoundException("Tag not found.", ErrorCodes.TAG_NOT_FOUND)).name;
        response.authorName = authorRepository.get(book.authorId).orElseThrow(() ->
            new NotFoundException("Author not found.", ErrorCodes.AUTHOR_NOT_FOUND)).name;
        response.publishingHouse = book.publishingHouse;
        response.description = book.description;
        response.mount = book.mount;
        return response;
    }

    private BorrowBookResponse borrowBookResponse(BorrowedRecord borrowedRecord) {
        BorrowBookResponse response = new BorrowBookResponse();
        response.userId = borrowedRecord.userId;
        response.userName = borrowedRecord.userName;
        response.bookId = borrowedRecord.bookId;
        response.bookName = borrowedRecord.bookName;
        response.borrowTime = borrowedRecord.borrowTime;
        response.returnTime = borrowedRecord.returnTime;
        return response;
    }

    private BorrowedRecordView borrowedRecordView(BorrowedRecord borrowedRecord) {
        BorrowedRecordView response = new BorrowedRecordView();
        response.id = borrowedRecord.id;
        response.userId = borrowedRecord.userId;
        response.userName = borrowedRecord.userName;
        response.bookId = borrowedRecord.bookId;
        response.bookName = borrowedRecord.bookName;
        response.borrowTime = borrowedRecord.borrowTime;
        response.returnTime = borrowedRecord.returnTime;
        response.isReturned = borrowedRecord.isReturned;
        return response;
    }
}
