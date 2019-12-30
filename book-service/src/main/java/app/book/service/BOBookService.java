package app.book.service;

import app.ErrorCodes;
import app.book.api.book.AuthorView;
import app.book.api.book.BOCreateAuthorRequest;
import app.book.api.book.BOCreateAuthorResponse;
import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOCreateCategoryRequest;
import app.book.api.book.BOCreateCategoryResponse;
import app.book.api.book.BOCreateTagRequest;
import app.book.api.book.BOCreateTagResponse;
import app.book.api.book.BOListAuthorResponse;
import app.book.api.book.BOListCategoryResponse;
import app.book.api.book.BOListTagResponse;
import app.book.api.book.BOSearchBookRequest;
import app.book.api.book.BOSearchBookResponse;
import app.book.api.book.BOSearchRecordResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.api.book.BookView;
import app.book.api.book.BorrowedRecordView;
import app.book.api.book.CategoryView;
import app.book.api.book.TagView;
import app.book.domain.Author;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Category;
import app.book.domain.Tag;
import com.mongodb.ReadPreference;
import com.mongodb.client.model.Filters;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
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

    public BookView get(Long bookId) {
        Optional<Book> book = bookRepository.get(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("book not found", ErrorCodes.BOOK_NOT_FOUND);
        }
        return convert(book.get());
    }

    public BOSearchBookResponse search(BOSearchBookRequest request) {
        BOSearchBookResponse response = new BOSearchBookResponse();
        response.books = select(request);
        response.total = response.books.size();
        return response;
    }

    public BOListCategoryResponse listCategory() {
        BOListCategoryResponse response = new BOListCategoryResponse();
        Query<Category> query = categoryRepository.select();
        response.categories = query.fetch().stream().map(category -> {
            CategoryView categoryView = new CategoryView();
            categoryView.categoryId = category.id;
            categoryView.categoryName = category.name;
            return categoryView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOListTagResponse listTag() {
        BOListTagResponse response = new BOListTagResponse();
        Query<Tag> query = tagRepository.select();
        response.tags = query.fetch().stream().map(tag -> {
            TagView tagView = new TagView();
            tagView.tagId = tag.id;
            tagView.tagName = tag.name;
            return tagView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOListAuthorResponse listAuthor() {
        BOListAuthorResponse response = new BOListAuthorResponse();
        Query<Author> query = authorRepository.select();
        response.authors = query.fetch().stream().map(author -> {
            AuthorView authorView = new AuthorView();
            authorView.authorId = author.id;
            authorView.authorName = author.name;
            return authorView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateBookResponse create(BOCreateBookRequest request) {
        BOCreateBookResponse response = new BOCreateBookResponse();
        response.id = bookRepository.insert(convert(request)).orElseThrow();
        response.name = request.name;
        return response;
    }

    public BOCreateCategoryResponse createCategory(BOCreateCategoryRequest request) {
        if (Strings.isBlank(request.categoryName)) {
            throw new BadRequestException("category name must be not null", ErrorCodes.NULL_CATEGORY);
        }
        BOCreateCategoryResponse response = new BOCreateCategoryResponse();
        Category category = new Category();
        category.name = request.categoryName;
        response.id = (int) categoryRepository.insert(category).orElseThrow();
        response.categoryName = request.categoryName;
        return response;
    }

    public BOCreateTagResponse createTag(BOCreateTagRequest request) {
        if (Strings.isBlank(request.tagName)) {
            throw new BadRequestException("tag name must be not null", ErrorCodes.NULL_TAG);
        }
        BOCreateTagResponse response = new BOCreateTagResponse();
        Tag tag = new Tag();
        tag.name = request.tagName;
        response.id = (int) tagRepository.insert(tag).orElseThrow();
        response.tagName = request.tagName;
        return response;
    }

    public BOCreateAuthorResponse createAuthor(BOCreateAuthorRequest request) {
        if (Strings.isBlank(request.authorName)) {
            throw new BadRequestException("author name must be not null", ErrorCodes.NULL_AUTHOR);
        }
        BOCreateAuthorResponse response = new BOCreateAuthorResponse();
        Author author = new Author();
        author.name = request.authorName;
        response.id = (int) authorRepository.insert(author).orElseThrow();
        response.authorName = request.authorName;
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

    public BOSearchRecordResponse searchRecordByBookId(Long bookId) {
        BOSearchRecordResponse response = new BOSearchRecordResponse();
        core.framework.mongo.Query query = new core.framework.mongo.Query();
        query.filter = Filters.eq("book_id", bookId);
        query.readPreference = ReadPreference.secondaryPreferred();
        List<BorrowedRecord> borrowedRecordList = collection.find(query);
        response.borrowedRecords = borrowedRecordList.stream().map(this::convert).collect(Collectors.toList());
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
        if (!Strings.isBlank(request.pub)) {
            where("books.pub like ?", Strings.format("%{}%", request.pub), whereClause, params);
        }
        if (!Strings.isBlank(request.description)) {
            where("books.description like ?", Strings.format("%{}%", request.description), whereClause, params);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT `books`.`id` as `id`, `books`.`name` as `name`, `authors`.`name` as `author_name`,");
        sql.append("`categories`.`name` as `category_name`, tags.`name` as `tag_name`, books.`pub`, books.`description` , books.`num`");
        sql.append("FROM `books` join `categories` join tags join `authors`");
        sql.append("on books.category_id = categories.id ");
        sql.append("and tags.id = books.tag_id ");
        sql.append("and `authors`.id = books.author_id ");
        sql.append("where ");
        sql.append(whereClause);
        logger.debug("sql={}, params={}", sql.toString(), params.toArray());
        return database.select(sql.toString(), BookView.class, params.toArray());
    }

    private void where(String condition, String param, StringBuilder whereClause, List<String> params) {
        if (Strings.isBlank(condition)) throw new Error("condition must not be blank");
        if (whereClause.length() > 0) whereClause.append(" AND ");
        whereClause.append(condition);
        params.add(param);
    }

    private Book convert(BOCreateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.authorId = request.authorId;
        book.categoryId = request.categoryId;
        book.tagId = request.tagId;
        book.pub = request.pub;
        book.description = request.description;
        book.num = request.num;
        return book;
    }

    private Book convert(BOUpdateBookRequest request) {
        Book book = new Book();
        book.name = request.name;
        book.authorId = request.authorId;
        book.categoryId = request.categoryId;
        book.tagId = request.tagId;
        book.pub = request.pub;
        book.description = request.description;
        book.num = request.num;
        return book;
    }

    private void convert(BOUpdateBookRequest request, BOUpdateBookResponse response) {
        response.name = request.name;
        response.authorId = request.authorId;
        response.categoryId = request.categoryId;
        response.tagId = request.tagId;
        response.pub = request.pub;
        response.description = request.description;
        response.num = request.num;
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
