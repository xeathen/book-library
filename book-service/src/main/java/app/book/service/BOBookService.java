package app.book.service;

import app.book.api.book.BOCreateBookRequest;
import app.book.api.book.BOCreateBookResponse;
import app.book.api.book.BOUpdateBookRequest;
import app.book.api.book.BOUpdateBookResponse;
import app.book.domain.Book;
import core.framework.db.Repository;
import core.framework.inject.Inject;

/**
 * @author Ethan
 */
public class BOBookService {
    @Inject
    Repository<Book> bookRepository;

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

    public Book convert(BOCreateBookRequest request) {
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

    public Book convert(BOUpdateBookRequest request) {
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

    public void convert(BOUpdateBookRequest request, BOUpdateBookResponse response) {
        response.name = request.name;
        response.author = request.author;
        response.pub = request.pub;
        response.category = request.category;
        response.tag = request.tag;
        response.description = request.description;
        response.num = request.num;
    }
}
