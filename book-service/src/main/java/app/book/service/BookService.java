package app.book.service;

import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.domain.Book;
import app.book.domain.BorrowedRecords;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    Repository<Book> bookRepository;
    @Inject
    MongoCollection<BorrowedRecords> mongoCollection;

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

    public BorrowBookResponse borrow(BorrowBookRequest request) {
        BorrowBookResponse response = new BorrowBookResponse();
        Optional<Book> book = bookRepository.get(request.bookId);
        if (book.isPresent() && book.get().num <= 0) {
//            throw new NotFoundException()
        }
        return response;
    }

    public GetBookResponse convert(Book book) {
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
}
