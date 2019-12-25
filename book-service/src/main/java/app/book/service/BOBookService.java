package app.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchHistoryResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.GetBorrowedRecordResponse;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import com.mongodb.ReadPreference;
import com.mongodb.client.model.Filters;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOBookService {
    @Inject
    Repository<Book> bookRepository;
    @Inject
    MongoCollection<BorrowedRecord> collection;

    public BOCreateBookResponse create(BOCreateBookRequest request) {
        BOCreateBookResponse response = new BOCreateBookResponse();
        response.id = bookRepository.insert(convert(request)).orElseThrow();
        response.name = request.name;

        return response;
    }

    public BOUpdateBookResponse update(Long id, BOUpdateBookRequest request) {
        BOUpdateBookResponse response = new BOUpdateBookResponse();
        Book book = convert(request);
        book.id = id;
        bookRepository.partialUpdate(book);
        response.id = id;
        convert(request, response);
        return response;
    }

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        BOSearchBookResponse response = new BOSearchBookResponse();
        Query<Book> query = bookRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        where(request, query);
        response.books = query.fetch().stream().map(this::convert).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOSearchHistoryResponse getBorrowedHistory(Long bookId) {
        BOSearchHistoryResponse response = new BOSearchHistoryResponse();
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.eq("book_id", bookId);
        query.readPreference = ReadPreference.secondaryPreferred();
        List<BorrowedRecord> borrowedRecordList = collection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::convert).collect(Collectors.toList());
        response.total = borrowedRecordList.size();
        return response;
    }

    private void where(BOSearchBookRequest request, Query query) {
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

    private Book convert(BOCreateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.author = request.author;
        book.pub = request.pub;
        book.category = request.category;
        book.tag = request.tag;
        book.description = request.description;
        book.num = request.num;
        return book;
    }

    private Book convert(BOUpdateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.author = request.author;
        book.pub = request.pub;
        book.category = request.category;
        book.tag = request.tag;
        book.description = request.description;
        book.num = request.num;
        return book;
    }

    private void convert(BOUpdateBookRequest request, BOUpdateBookResponse response) {
        response.name = request.name;
        response.author = request.author;
        response.pub = request.pub;
        response.category = request.category;
        response.tag = request.tag;
        response.description = request.description;
        response.num = request.num;
    }

    private GetBookResponse convert(Book book) {
        GetBookResponse response = new GetBookResponse();
        response.name = book.name;
        response.author = book.author;
        response.pub = book.pub;
        response.category = book.category;
        response.tag = book.tag;
        response.description = book.description;
        response.num = book.num;
        return response;
    }

    private GetBorrowedRecordResponse convert(BorrowedRecord borrowedRecord) {
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
