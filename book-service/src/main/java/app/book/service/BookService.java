package app.book.service;

import app.ErrorCodes;
import app.book.api.book.BookView;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.BorrowedRecordView;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchRecordResponse;
import app.book.domain.Author;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Category;
import app.book.domain.Reservation;
import app.book.domain.Tag;
import app.user.api.UserWebService;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserView;
import com.mongodb.ReadPreference;
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
    UserWebService userWebService;
    @Inject
    Database database;

    public BookView get(Long bookId) {
        Optional<Book> book = bookRepository.get(bookId);
        book.orElseThrow(() -> new NotFoundException("book not found", ErrorCodes.BOOK_NOT_FOUND));
        return convert(book.get());
    }

    public SearchBookResponse search(SearchBookRequest request) {
        SearchBookResponse response = new SearchBookResponse();
        response.books = select(request);
        response.total = response.books.size();
        return response;
    }

    public SearchRecordResponse searchRecordByUserId(Long userId) {
        SearchRecordResponse response = new SearchRecordResponse();
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.eq("user_id", userId);
        query.readPreference = ReadPreference.secondaryPreferred();
        List<BorrowedRecord> borrowedRecordList = mongoCollection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::convert).collect(Collectors.toList());
        response.total = borrowedRecordList.size();
        return response;
    }

    public BorrowBookResponse borrowBook(BorrowBookRequest request) {
        BorrowBookResponse response = new BorrowBookResponse();
        Optional<Book> book = bookRepository.get(request.bookId);
        UserView userView = userWebService.get(request.userId);
        if (book.isEmpty()) {
            throw new NotFoundException("Book not found.", ErrorCodes.BOOK_NOT_FOUND);
        }
        if (book.get().num <= 0) {
            throw new BadRequestException("No book rest.", ErrorCodes.NO_BOOK_REST);
        }
        if (userView == null) {
            throw new NotFoundException("User not found.", ErrorCodes.USER_NOT_FOUND);
        }
        if (userView.status == UserStatusView.INACTIVE) {
            throw new ConflictException("You are banned!", ErrorCodes.BANNED);
        }
        if (!isReturned(request.userId, request.bookId)) {
            throw new ConflictException("You had borrowed this book already.", ErrorCodes.BORROWED_ALREADY);
        }
        if (ZonedDateTime.now().isAfter(request.returnTime)) {
            throw new BadRequestException("ReturnTime is past.", ErrorCodes.RETURN_TIME_PAST);
        }
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.id = UUID.randomUUID().toString();
        borrowedRecord.userId = request.userId;
        borrowedRecord.userName = userView.userName;
        borrowedRecord.bookId = request.bookId;
        borrowedRecord.bookName = book.get().name;
        borrowedRecord.borrowTime = ZonedDateTime.now();
        borrowedRecord.returnTime = request.returnTime;
        borrowedRecord.isReturned = Boolean.FALSE;

        mongoCollection.insert(borrowedRecord);
        Integer num = bookRepository.get(request.bookId).get().num;
        Book borrowedBook = new Book();
        borrowedBook.id = request.bookId;
        borrowedBook.num = num - 1;
        bookRepository.partialUpdate(borrowedBook);
        convert(borrowedRecord, response);
        return response;
    }

    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        ReturnBookResponse response = new ReturnBookResponse();
        if (isReturned(request.userId, request.bookId)) {
            throw new NotFoundException("record not found", ErrorCodes.RECORD_NOT_FOUND);
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

        Integer num = bookRepository.get(request.bookId).orElseThrow(() -> new NotFoundException("book not found", ErrorCodes.BOOK_NOT_FOUND)).num;
        Book borrowedBook = new Book();
        borrowedBook.id = request.bookId;
        borrowedBook.num = num + 1;
        bookRepository.partialUpdate(borrowedBook);
        return response;
    }

    public CreateReservationResponse reserve(CreateReservationRequest request) {
        Query<Reservation> query = reservationRepository.select();
        query.where("user_id = ?", request.userId);
        query.where("book_id = ?", request.bookId);
        List<Reservation> collect = new ArrayList<>(query.fetch());
        if (!collect.isEmpty()) {
            throw new BadRequestException("you have reserved this book already", ErrorCodes.ALREADY_RESERVED);
        }
        CreateReservationResponse response = new CreateReservationResponse();
        Reservation reservation = new Reservation();
        reservation.userId = request.userId;
        reservation.bookId = request.bookId;
        reservation.reserveTime = ZonedDateTime.now();
        reservationRepository.insert(reservation);
        response.userId = request.userId;
        response.userName = userWebService.get(request.userId).userName;
        response.bookId = request.bookId;
        response.bookName = bookRepository.get(request.bookId).orElseThrow().name;
        return response;
    }

    private List<BookView> select(SearchBookRequest request) {
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
        if (!Strings.isBlank(request.pub)) {
            where("books.pub like ?", Strings.format("%{}%", request.pub), whereClause, params);
        }
        if (!Strings.isBlank(request.description)) {
            where("books.description like ?", Strings.format("%{}%", request.description), whereClause, params);
        }
        String sql = "SELECT `books`.`id` as `id`, `books`.`name` as `name`, `authors`.`name` as `author_name`,"
            + "`categories`.`name` as `category_name`, tags.`name` as `tag_name`, books.`pub`, books.`description` , books.`num`"
            + "FROM `books` join `categories` join tags join `authors`"
            + "on books.category_id = categories.id and tags.id = books.tag_id and `authors`.id = books.author_id "
            + "where " + whereClause;
        return database.select(sql, BookView.class, params.toArray());
    }

    private void where(String condition, String param, StringBuilder whereClause, List<String> params) {
        if (Strings.isBlank(condition)) throw new Error("condition must not be blank");
        if (whereClause.length() > 0) whereClause.append(" AND ");
        whereClause.append(condition);
        params.add(param);
    }

    private Boolean isReturned(Long userId, Long bookId) {
        List<BorrowedRecord> borrowedRecordList = getNotReturnedRecordList(userId, bookId);
        if (!borrowedRecordList.isEmpty()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private List<BorrowedRecord> getNotReturnedRecordList(Long userId, Long bookId) {
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.and(Filters.eq("book_id", bookId),
            Filters.eq("user_id", userId),
            Filters.eq("is_returned", Boolean.FALSE));
        query.readPreference = ReadPreference.secondaryPreferred();
        return mongoCollection.find(query);
    }

    private BookView convert(Book book) {
        BookView response = new BookView();
        response.id = book.id;
        response.name = book.name;
        response.categoryName = categoryRepository.get(book.categoryId).orElseThrow(() ->
            new NotFoundException("category not found", ErrorCodes.CATEGORY_NOT_FOUND)).name;
        response.tagName = tagRepository.get(book.tagId).orElseThrow(() ->
            new NotFoundException("tag not found", ErrorCodes.TAG_NOT_FOUND)).name;
        response.authorName = authorRepository.get(book.authorId).orElseThrow(() ->
            new NotFoundException("author not found", ErrorCodes.AUTHOR_NOT_FOUND)).name;
        response.pub = book.pub;
        response.description = book.description;
        response.num = book.num;
        return response;
    }

    private void convert(BorrowedRecord borrowedRecord, BorrowBookResponse response) {
        response.userId = borrowedRecord.userId;
        response.userName = borrowedRecord.userName;
        response.bookId = borrowedRecord.bookId;
        response.bookName = borrowedRecord.bookName;
        response.borrowTime = borrowedRecord.borrowTime;
        response.returnTime = borrowedRecord.returnTime;
    }

    private BorrowedRecordView convert(BorrowedRecord borrowedRecord) {
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
