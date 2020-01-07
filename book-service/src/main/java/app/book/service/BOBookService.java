package app.book.service;

import app.ErrorCodes;
import app.author.domain.Author;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordRequest;
import app.book.api.book.BOSearchRecordResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.api.book.BookView;
import app.book.api.book.BorrowedRecordView;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.category.domain.Category;
import app.tag.domain.Tag;
import com.mongodb.client.model.Filters;
import core.framework.db.Database;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOBookService {
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Repository<Category> categoryRepository;
    @Inject
    Repository<Tag> tagRepository;
    @Inject
    Repository<Author> authorRepository;
    @Inject
    MongoCollection<BorrowedRecord> borrowedRecordCollection;
    @Inject
    Database database;
    @Inject
    ReservationService reservationService;

    public BOGetBookResponse get(Long bookId) {
        return boGetBookResponse(bookRepository.get(bookId).orElseThrow(() ->
            new NotFoundException("book not found, id=" + bookId, ErrorCodes.BOOK_NOT_FOUND)));
    }

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        List<String> params = new ArrayList<>();
        String selectSQL = "SELECT books.id AS id, books.name AS name, authors.name AS author_name, "
            + "categories.name AS category_name, tags.name AS tag_name, "
            + "books.publishing_house, books.description , books.quantity ";
        String fromSQL = "FROM books JOIN categories JOIN tags JOIN authors "
            + "ON books.category_id = categories.id AND tags.id = books.tag_id AND authors.id = books.author_id ";
        String whereSQL = whereSQL(request, params);
        String limitSQL = "limit " + request.skip + ", " + request.limit;
        BOSearchBookResponse response = new BOSearchBookResponse();
        response.books = database.select(selectSQL + fromSQL + whereSQL + limitSQL, BookView.class, params.toArray());
        String countSQL = "SELECT COUNT(1) ";
        response.total = database.selectOne(countSQL + fromSQL + whereSQL, Integer.class, params.toArray()).orElse(0);
        return response;
    }

    public BOCreateBookResponse create(BOCreateBookRequest request) {
        BOCreateBookResponse response = new BOCreateBookResponse();
        response.id = bookRepository.insert(book(request)).orElseThrow();
        response.name = request.name;
        return response;
    }

    public BOUpdateBookResponse update(Long id, BOUpdateBookRequest request) {
        Book book = book(request);
        book.id = id;
        bookRepository.partialUpdate(book);
        reservationService.notifyAvailability();
        BOUpdateBookResponse response = boUpdateBookResponse(bookRepository.get(id).orElseThrow());
        response.id = id;
        return response;
    }

    public BOSearchRecordResponse searchRecord(BOSearchRecordRequest request) {
        BOSearchRecordResponse response = new BOSearchRecordResponse();
        Query query = new Query();
        query.skip = request.skip;
        query.limit = request.limit;
        query.filter = Filters.eq("book_id", request.bookId);
        List<BorrowedRecord> borrowedRecordList = borrowedRecordCollection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::borrowedRecordView).collect(Collectors.toList());
        response.total = borrowedRecordCollection.count(query.filter);
        return response;
    }

    private BOGetBookResponse boGetBookResponse(Book book) {
        BOGetBookResponse response = new BOGetBookResponse();
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

    private String whereSQL(BOSearchBookRequest request, List<String> params) {
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

    private Book book(BOCreateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.authorId = request.authorId;
        book.categoryId = request.categoryId;
        book.tagId = request.tagId;
        book.publishingHouse = request.publishingHouse;
        book.description = request.description;
        book.quantity = request.quantity;
        return book;
    }

    private Book book(BOUpdateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.authorId = request.authorId;
        book.categoryId = request.categoryId;
        book.tagId = request.tagId;
        book.publishingHouse = request.publishingHouse;
        book.description = request.description;
        book.quantity = request.quantity;
        return book;
    }

    private BOUpdateBookResponse boUpdateBookResponse(Book book) {
        BOUpdateBookResponse response = new BOUpdateBookResponse();
        response.name = book.name;
        response.authorId = book.authorId;
        response.categoryId = book.categoryId;
        response.tagId = book.tagId;
        response.publishingHouse = book.publishingHouse;
        response.description = book.description;
        response.quantity = book.quantity;
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
        response.expectedReturnTime = borrowedRecord.expectedReturnTime;
        response.actualReturnTime = borrowedRecord.actualReturnTime;
        return response;
    }
}
