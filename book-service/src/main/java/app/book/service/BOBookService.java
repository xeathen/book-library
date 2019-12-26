package app.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOGetAuthorResponse;
import app.book.api.book.BOGetBookResponse;
import app.book.api.book.BOGetCategoryResponse;
import app.book.api.book.BOGetTagResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchHistoryResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.api.book.GetBorrowedRecordResponse;
import app.book.domain.Author;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Category;
import app.book.domain.Tag;
import com.mongodb.ReadPreference;
import com.mongodb.client.model.Filters;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
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
    MongoCollection<BorrowedRecord> collection;

    public BOGetBookResponse get(Long bookId) {
        Optional<Book> book = bookRepository.get(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("book not found");
        }
        return convert(book.get());
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

    public BOGetCategoryResponse getCategories() {
        BOGetCategoryResponse response = new BOGetCategoryResponse();
        Query<Category> query = categoryRepository.select();
        response.categories = query.fetch().stream().map(category -> category.name).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOGetTagResponse getTags() {
        BOGetTagResponse response = new BOGetTagResponse();
        Query<Tag> query = tagRepository.select();
        response.tags = query.fetch().stream().map(tag -> tag.name).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOGetAuthorResponse getAuthors() {
        BOGetAuthorResponse response = new BOGetAuthorResponse();
        Query<Author> query = authorRepository.select();
        response.authors = query.fetch().stream().map(author -> author.name).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateBookResponse create(BOCreateBookRequest request) {
        BOCreateBookResponse response = new BOCreateBookResponse();
        response.id = bookRepository.insert(convert(request)).orElseThrow();
        response.name = request.name;
        return response;
    }

    public BOCreateBookResponse createInCategory(BOCreateBookRequest request) {
        if (Strings.isBlank(request.category)) {
            throw new BadRequestException("category must be not null");
        }
        List<Category> select = categoryRepository.select("name = ?", request.category);
        if (select.isEmpty()) {
            Category category = new Category();
            category.name = request.category;
            categoryRepository.insert(category);
        }
        BOCreateBookResponse response = new BOCreateBookResponse();
        response.id = bookRepository.insert(convert(request)).orElseThrow();
        response.name = request.name;
        return response;
    }

    public BOCreateBookResponse createInTag(BOCreateBookRequest request) {
        if (Strings.isBlank(request.tag)) {
            throw new BadRequestException("tag must be not null");
        }
        List<Tag> select = tagRepository.select("name = ?", request.tag);
        if (select.isEmpty()) {
            Tag tag = new Tag();
            tag.name = request.tag;
            tagRepository.insert(tag);
        }
        BOCreateBookResponse response = new BOCreateBookResponse();
        response.id = bookRepository.insert(convert(request)).orElseThrow();
        response.name = request.name;
        return response;
    }

    public BOCreateBookResponse createInAuthor(BOCreateBookRequest request) {
        if (Strings.isBlank(request.author)) {
            throw new BadRequestException("author must be not null");
        }
        List<Author> select = authorRepository.select("name = ?", request.author);
        if (select.isEmpty()) {
            Author author = new Author();
            author.name = request.author;
            authorRepository.insert(author);
        }
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

    private void where(BOSearchBookRequest request, Query<Book> query) {
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

    private BOGetBookResponse convert(Book book) {
        BOGetBookResponse response = new BOGetBookResponse();
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
