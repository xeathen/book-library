package app.book.service;

import app.ErrorCodes;
import app.author.domain.Author;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
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
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOBookService {
    private final Logger logger = LoggerFactory.getLogger(BOBookService.class);
    @Inject
    Repository<Book> bookRepository;
    @Inject
    Repository<Category> categoryRepository;
    @Inject
    Repository<Tag> tagRepository;
    @Inject
    Repository<Author> authorRepository;
    @Inject
    MongoCollection<BorrowedRecord> collection;
    @Inject
    Database database;

    public BOGetBookResponse get(Long bookId) {
        Optional<Book> book = bookRepository.get(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("book not found", ErrorCodes.BOOK_NOT_FOUND);
        }
        return boGetBookResponse(book.get());
    }

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        BOSearchBookResponse response = new BOSearchBookResponse();
        response.books = select(request);
        response.total = response.books.size();
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
        BOUpdateBookResponse response = boUpdateBookResponse(request);
        response.id = id;
        return response;
    }

    public BOSearchRecordResponse searchRecordByBookId(Long bookId) {
        BOSearchRecordResponse response = new BOSearchRecordResponse();
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.eq("book_id", bookId);
        List<BorrowedRecord> borrowedRecordList = collection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::borrowedRecordView).collect(Collectors.toList());
        response.total = borrowedRecordList.size();
        return response;
    }

    private List<BookView> select(BOSearchBookRequest request) {
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
        String sql = "SELECT `books`.`id` as `id`, `books`.`name` as `name`, `authors`.`name` as `author_name`,"
            + "`categories`.`name` as `category_name`, tags.`name` as `tag_name`, books.`publishing_house`, books.`description` , books.`mount`"
            + "FROM `books` join `categories` join tags join `authors`"
            + "on books.category_id = categories.id and tags.id = books.tag_id and `authors`.id = books.author_id "
            + "where " + (whereClause.length() > 0 ? whereClause : "1 = 1");
        return database.select(sql, BookView.class, params.toArray());
    }

    private void where(String condition, String param, StringBuilder whereClause, List<String> params) {
        if (Strings.isBlank(condition)) throw new Error("condition must not be blank");
        if (whereClause.length() > 0) whereClause.append(" AND ");
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
        book.mount = request.mount;
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
        book.mount = request.mount;
        return book;
    }

    private BOUpdateBookResponse boUpdateBookResponse(BOUpdateBookRequest request) {
        BOUpdateBookResponse response = new BOUpdateBookResponse();
        response.name = request.name;
        response.authorId = request.authorId;
        response.categoryId = request.categoryId;
        response.tagId = request.tagId;
        response.publishingHouse = request.publishingHouse;
        response.description = request.description;
        response.mount = request.mount;
        return response;
    }

    private BOGetBookResponse boGetBookResponse(Book book) {
        BOGetBookResponse response = new BOGetBookResponse();
        response.id = book.id;
        response.name = book.name;
        response.categoryName = categoryRepository.get(book.categoryId).orElseThrow(() ->
            new NotFoundException("category not found", ErrorCodes.CATEGORY_NOT_FOUND)).name;
        response.tagName = tagRepository.get(book.tagId).orElseThrow(() ->
            new NotFoundException("tag not found", ErrorCodes.TAG_NOT_FOUND)).name;
        response.authorName = authorRepository.get(book.authorId).orElseThrow(() ->
            new NotFoundException("author not found", ErrorCodes.AUTHOR_NOT_FOUND)).name;
        response.publishingHouse = book.publishingHouse;
        response.description = book.description;
        response.mount = book.mount;
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
