package app.book.service;

import app.ErrorCodes;
import app.author.domain.Author;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.BorrowedRecordView;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBackBookRequest;
import app.book.api.book.ReturnBackBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchRecordRequest;
import app.book.api.book.SearchRecordResponse;
import app.book.api.kafka.BorrowedRecordExpirationMessage;
import app.book.domain.Book;
import app.book.domain.BookView;
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
import core.framework.kafka.MessagePublisher;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.ForbiddenException;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookService.class);
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
    MongoCollection<BorrowedRecord> borrowedRecordCollection;
    @Inject
    BOUserWebService boUserWebService;
    @Inject
    Database database;
    @Inject
    ReservationService reservationService;
    @Inject
    MessagePublisher<BorrowedRecordExpirationMessage> expirationMessagePublisher;

    public GetBookResponse get(Long bookId) {
        Book book = bookRepository.get(bookId).orElseThrow(() ->
            new NotFoundException("Book not found, id=" + bookId, ErrorCodes.BOOK_NOT_FOUND));
        return getBookResponse(book);
    }

    public SearchBookResponse search(SearchBookRequest request) {
        List<String> params = new ArrayList<>();
        String selectSQL = "SELECT books.id AS id, books.name AS name, authors.name AS author_name, "
            + "categories.name AS category_name, tags.name AS tag_name, "
            + "books.publishing_house, books.description , books.quantity ";
        String fromSQL = "FROM books JOIN categories JOIN tags JOIN authors "
            + "ON books.category_id = categories.id AND tags.id = books.tag_id AND authors.id = books.author_id ";
        String whereSQL = whereSQL(request, params);
        String limitSQL = "LIMIT " + request.skip + ", " + request.limit;
        SearchBookResponse response = new SearchBookResponse();
        response.books = database.select(selectSQL + fromSQL + whereSQL + limitSQL, BookView.class, params.toArray())
            .stream().map(this::getBookResponse).collect(Collectors.toList());
        String countSQL = "SELECT COUNT(1) ";
        response.total = database.selectOne(countSQL + fromSQL + whereSQL, Integer.class, params.toArray()).orElse(0);
        return response;
    }

    public BorrowBookResponse borrow(Long bookId, BorrowBookRequest request) {
        BOGetUserResponse user = boUserWebService.get(request.userId);
        if (user.status == UserStatusView.INACTIVE) {
            throw new ConflictException("You are banned!", ErrorCodes.BANNED);
        }
        Book book = bookRepository.get(bookId).orElseThrow(() ->
            new NotFoundException("Book not found, id=" + bookId, ErrorCodes.BOOK_NOT_FOUND));
        if (getNotReturnedRecord(request.userId, bookId) != null) {
            throw new ConflictException("You had borrowed this book already.", ErrorCodes.BORROWED_ALREADY);
        }
        if (LocalDateTime.now().isAfter(request.expectedReturnTime)) {
            throw new ForbiddenException(ErrorCodes.TIME_IS_PAST);
        }
        if (book.quantity <= 0) {
            throw new ForbiddenException(ErrorCodes.NO_BOOK_REST);
        }
        updateBookQuantity(bookId, book.quantity - 1);
        BorrowedRecord borrowedRecord = createBorrowedRecord(request, book, user);
        borrowedRecordCollection.insert(borrowedRecord);
        return borrowBookResponse(borrowedRecord);
    }

    public ReturnBackBookResponse returnBack(Long bookId, ReturnBackBookRequest request) {
        BorrowedRecord borrowedRecord = getNotReturnedRecord(request.userId, bookId);
        if (borrowedRecord == null) {
            throw new NotFoundException("You haven't borrowed this book.", ErrorCodes.RECORD_NOT_FOUND);
        }
        if (borrowedRecord.expectedReturnTime.isBefore(LocalDateTime.now())) {
            throw new ForbiddenException("The return time is past.");
        }
        Book book = bookRepository.get(bookId).orElseThrow(() ->
            new NotFoundException("Book not found, id=" + bookId, ErrorCodes.BOOK_NOT_FOUND));
        Integer previousQuantity = book.quantity;
        updateBookQuantity(book.id, book.quantity + 1);
        borrowedRecord.actualReturnTime = LocalDateTime.now();
        borrowedRecordCollection.replace(borrowedRecord);
        if (previousQuantity == 0) {
            reservationService.notifyReservationAvailability(bookId, book.name);
        }
        ReturnBackBookResponse response = new ReturnBackBookResponse();
        response.actualReturnTime = LocalDateTime.now();
        return response;
    }

    public CreateReservationResponse reserve(Long bookId, CreateReservationRequest request) {
        Book book = bookRepository.get(bookId).orElseThrow(() ->
            new NotFoundException("Book not found, id=" + bookId, ErrorCodes.BOOK_NOT_FOUND));
        if (book.quantity > 0) {
            throw new ForbiddenException("The book is enough yet, id=" + bookId);
        }
        Query<Reservation> reservationQuery = reservationRepository.select();
        reservationQuery.where("user_id = ?", request.userId);
        reservationQuery.where("book_id = ?", bookId);
        if (reservationQuery.fetchOne().isPresent()) {
            throw new ForbiddenException(ErrorCodes.ALREADY_RESERVED);
        }
        createReservation(request.userId, bookId);
        return createReservationResponse(bookId, book.name);
    }

    public SearchRecordResponse searchRecord(SearchRecordRequest request) {
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.skip = request.skip;
        query.limit = request.limit;
        query.filter = Filters.eq("user_id", request.userId);
        List<BorrowedRecord> borrowedRecordList = borrowedRecordCollection.find(query);
        SearchRecordResponse response = new SearchRecordResponse();
        response.borrowedRecords = borrowedRecordList.stream().map(this::borrowedRecordView).collect(Collectors.toList());
        response.total = borrowedRecordCollection.count(query.filter);
        return response;
    }

    public void notifyRecordExpiration() {
        core.framework.mongo.Query recordQuery = new core.framework.mongo.Query();
        LocalDate today = LocalDate.now();
        recordQuery.filter = Filters.and(
            Filters.eq("actual_return_time", null),
            Filters.regex("expected_return_time", today.plusDays(1).toString()));
        borrowedRecordCollection.find(recordQuery).forEach(borrowedRecord -> {
            logger.info("publish recordExpirationMessage, userId={}, bookId={}, borrowTime={}",
                borrowedRecord.userId, borrowedRecord.bookId, borrowedRecord.borrowTime);
            BorrowedRecordExpirationMessage message = new BorrowedRecordExpirationMessage();
            message.username = borrowedRecord.username;
            message.email = boUserWebService.get(borrowedRecord.userId).email;
            message.bookName = borrowedRecord.bookName;
            expirationMessagePublisher.publish(borrowedRecord.id, message);
        });
    }

    private GetBookResponse getBookResponse(Book book) {
        GetBookResponse response = new GetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.categoryName = categoryRepository.get(book.categoryId).orElseThrow(() ->
            new NotFoundException("Category not found, id=" + book.categoryId, ErrorCodes.CATEGORY_NOT_FOUND)).name;
        response.tagName = tagRepository.get(book.tagId).orElseThrow(() ->
            new NotFoundException("Tag not found, id=" + book.tagId, ErrorCodes.TAG_NOT_FOUND)).name;
        response.authorName = authorRepository.get(book.authorId).orElseThrow(() ->
            new NotFoundException("Author not found, id=" + book.authorId, ErrorCodes.AUTHOR_NOT_FOUND)).name;
        response.publishingHouse = book.publishingHouse;
        response.description = book.description;
        response.quantity = book.quantity;
        return response;
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
        if (Strings.isBlank(condition)) {
            return;
        }
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }
        whereClause.append(condition);
        params.add(param);
    }

    private GetBookResponse getBookResponse(BookView bookView) {
        GetBookResponse getBookResponse = new GetBookResponse();
        getBookResponse.id = bookView.id;
        getBookResponse.name = bookView.name;
        getBookResponse.authorName = bookView.authorName;
        getBookResponse.categoryName = bookView.categoryName;
        getBookResponse.tagName = bookView.tagName;
        getBookResponse.publishingHouse = bookView.publishingHouse;
        getBookResponse.description = bookView.description;
        getBookResponse.quantity = bookView.quantity;
        return getBookResponse;
    }

    private BorrowedRecord createBorrowedRecord(BorrowBookRequest request, Book book, BOGetUserResponse response) {
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.id = UUID.randomUUID().toString();
        borrowedRecord.userId = request.userId;
        borrowedRecord.username = response.username;
        borrowedRecord.bookId = book.id;
        borrowedRecord.bookName = book.name;
        borrowedRecord.borrowTime = LocalDateTime.now();
        borrowedRecord.expectedReturnTime = request.expectedReturnTime;
        return borrowedRecord;
    }

    private void updateBookQuantity(Long bookId, Integer updatedQuantity) {
        Book book = new Book();
        book.id = bookId;
        book.quantity = updatedQuantity;
        bookRepository.partialUpdate(book);
    }

    private BorrowBookResponse borrowBookResponse(BorrowedRecord borrowedRecord) {
        BorrowBookResponse response = new BorrowBookResponse();
        response.bookId = borrowedRecord.bookId;
        response.bookName = borrowedRecord.bookName;
        response.borrowTime = borrowedRecord.borrowTime;
        response.expectedReturnTime = borrowedRecord.expectedReturnTime;
        return response;
    }

    private BorrowedRecord getNotReturnedRecord(Long userId, Long bookId) {
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.and(Filters.eq("book_id", bookId),
            Filters.eq("user_id", userId),
            Filters.eq("actual_return_time", null));
        List<BorrowedRecord> borrowedRecordList = borrowedRecordCollection.find(query);
        return borrowedRecordList.isEmpty() ? null : borrowedRecordList.get(0);
    }

    private void createReservation(Long userId, Long bookId) {
        Reservation reservation = new Reservation();
        reservation.userId = userId;
        reservation.bookId = bookId;
        reservation.reserveTime = LocalDateTime.now();
        reservationRepository.insert(reservation);
    }

    private CreateReservationResponse createReservationResponse(Long bookId, String bookName) {
        CreateReservationResponse response = new CreateReservationResponse();
        response.bookId = bookId;
        response.bookName = bookName;
        return response;
    }

    private BorrowedRecordView borrowedRecordView(BorrowedRecord borrowedRecord) {
        BorrowedRecordView view = new BorrowedRecordView();
        view.id = borrowedRecord.id;
        view.userId = borrowedRecord.userId;
        view.username = borrowedRecord.username;
        view.bookId = borrowedRecord.bookId;
        view.bookName = borrowedRecord.bookName;
        view.borrowTime = borrowedRecord.borrowTime;
        view.expectedReturnTime = borrowedRecord.expectedReturnTime;
        view.actualReturnTime = borrowedRecord.actualReturnTime;
        return view;
    }
}
