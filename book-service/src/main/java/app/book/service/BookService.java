package app.book.service;

import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.GetBorrowedRecordResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchHistoryResponse;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Reservation;
import app.user.api.UserWebService;
import com.mongodb.ReadPreference;
import com.mongodb.client.model.Filters;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;

import java.time.ZonedDateTime;
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
    Repository<Reservation> reservationRepository;
    @Inject
    MongoCollection<BorrowedRecord> mongoCollection;
    @Inject
    UserWebService userWebService;

    public GetBookResponse get(Long id) {
        Optional<Book> book = bookRepository.get(id);
        if (book.isEmpty()) {
            throw new NotFoundException("book not found");
        }
        return convert(book.get());
    }

    public SearchBookResponse search(SearchBookRequest request) {
        SearchBookResponse response = new SearchBookResponse();
        Query<Book> query = bookRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        where(request, query);
        response.books = query.fetch().stream().map(this::convert).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public SearchHistoryResponse searchBorrowedHistory(Long userId) {
        SearchHistoryResponse response = new SearchHistoryResponse();
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.eq("user_id", userId);
        query.readPreference = ReadPreference.secondaryPreferred();
        List<BorrowedRecord> borrowedRecordList = mongoCollection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::recordConvert).collect(Collectors.toList());
        response.total = borrowedRecordList.size();
        return response;
    }

    public BorrowBookResponse borrowBook(BorrowBookRequest request) {
        BorrowBookResponse response = new BorrowBookResponse();
        Optional<Book> book = bookRepository.get(request.bookId);
        if (userWebService.get(request.userId) == null) {
            throw new NotFoundException("user not found, id=" + request.userId);
        }
        if (bookRepository.get(request.bookId).isEmpty()) {
            throw new NotFoundException("book not found, id=" + request.userId);
        }
        if (!isReturned(request.userId, request.bookId)) {
            throw new BadRequestException("you had borrowed this book already.");
        }
        if (book.isPresent() && book.get().num <= 0) {
            throw new BadRequestException("no book rest.");
        }
        if (ZonedDateTime.now().isAfter(request.returnTime)) {
            throw new BadRequestException("returnTime is after than now");
        }
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.id = UUID.randomUUID().toString();
        borrowedRecord.bookId = request.bookId;
        borrowedRecord.userId = request.userId;
        borrowedRecord.borrowTime = ZonedDateTime.now();
        borrowedRecord.returnTime = request.returnTime;
        borrowedRecord.isReturned = false;

        mongoCollection.insert(borrowedRecord);
        Integer num = bookRepository.get(request.bookId).get().num;
        Book borrowedBook = new Book();
        borrowedBook.id = request.bookId;
        borrowedBook.num = num - 1;
        bookRepository.partialUpdate(borrowedBook);

        response = convert(borrowedRecord);
        return response;
    }

    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        ReturnBookResponse response = new ReturnBookResponse();
        if (isReturned(request.userId, request.bookId)) {
            throw new NotFoundException("no record");
        }
        List<BorrowedRecord> borrowedRecordList = getNotReturnedRecordList(request.userId, request.bookId);
        BorrowedRecord record = borrowedRecordList.get(0);
        record.returnTime = ZonedDateTime.now();
        record.isReturned = true;
        mongoCollection.replace(record);
        response.bookId = request.bookId;
        response.userId = request.userId;
        response.returnTime = ZonedDateTime.now();

        Integer num = bookRepository.get(request.bookId).get().num;
        Book borrowedBook = new Book();
        borrowedBook.id = request.bookId;
        borrowedBook.num = num + 1;
        bookRepository.partialUpdate(borrowedBook);

        return response;
    }

    public CreateReservationResponse reserve(CreateReservationRequest request) {
        CreateReservationResponse response = new CreateReservationResponse();
        Reservation reservation = new Reservation();
        reservation.userId = request.userId;
        reservation.bookId = request.bookId;
        reservationRepository.insert(reservation);
        response.userId = request.userId;
        response.bookId = request.bookId;
        return response;
    }

    private void where(SearchBookRequest request, Query query) {
        if (!Strings.isBlank(request.name)) {
            query.where("name like ?", Strings.format("%{}%", request.name));
        }
        if (!Strings.isBlank(request.author)) {
            query.where("author like ?", Strings.format("%{}%", request.author));
        }
        if (!Strings.isBlank(request.pub)) {
            query.where("pub like ?", Strings.format("%{}%", request.pub));
        }
        if (!Strings.isBlank(request.category)) {
            query.where("category like ?", Strings.format("%{}%", request.category));
        }
        if (!Strings.isBlank(request.tag)) {
            query.where("tag like ?", Strings.format("%{}%", request.tag));
        }
        if (!Strings.isBlank(request.description)) {
            query.where("description like ?", Strings.format("%{}%", request.description));
        }
    }

    private Boolean isReturned(Long userId, Long bookId) {
        List<BorrowedRecord> borrowedRecordList = getNotReturnedRecordList(userId, bookId);
        if (borrowedRecordList.size() > 0) {
            return false;
        }
        return true;
    }

    private List<BorrowedRecord> getNotReturnedRecordList(Long userId, Long bookId) {
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.and(Filters.eq("book_id", bookId),
            Filters.eq("user_id", userId),
            Filters.eq("is_returned", false));
        query.readPreference = ReadPreference.secondaryPreferred();
        return mongoCollection.find(query);
    }

    private GetBookResponse convert(Book book) {
        GetBookResponse response = new GetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.author = book.author;
        response.pub = book.pub;
        response.category = book.category;
        response.tag = book.tag;
        response.description = book.description;
        response.num = book.num;
        return response;
    }

    private BorrowBookResponse convert(BorrowedRecord borrowedRecord) {
        BorrowBookResponse response = new BorrowBookResponse();
        response.userId = borrowedRecord.userId;
        response.bookId = borrowedRecord.bookId;
        response.borrowTime = borrowedRecord.borrowTime;
        response.returnTime = borrowedRecord.returnTime;
        return response;
    }

    private GetBorrowedRecordResponse recordConvert(BorrowedRecord borrowedRecord) {
        GetBorrowedRecordResponse response = new GetBorrowedRecordResponse();
        response.id = borrowedRecord.id;
        response.userId = borrowedRecord.userId;
        response.bookId = borrowedRecord.bookId;
        response.borrowTime = borrowedRecord.borrowTime;
        response.returnTime = borrowedRecord.returnTime;
        response.isReturned = borrowedRecord.isReturned;
        return response;
    }
}
