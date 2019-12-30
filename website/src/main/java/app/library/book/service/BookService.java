package app.library.book.service;

import app.book.api.BookWebService;
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
import app.library.api.book.BookAJAXView;
import app.library.api.book.BorrowBookAJAXRequest;
import app.library.api.book.BorrowBookAJAXResponse;
import app.library.api.book.CreateReservationAJAXRequest;
import app.library.api.book.CreateReservationAJAXResponse;
import app.library.api.book.GetBorrowedRecordAJAXResponse;
import app.library.api.book.ReturnBookAJAXRequest;
import app.library.api.book.ReturnBookAJAXResponse;
import app.library.api.book.SearchBookAJAXRequest;
import app.library.api.book.SearchBookAJAXResponse;
import app.library.api.book.SearchRecordByUserIdAJAXResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    BookWebService bookWebService;

    public BookAJAXView get(Long id) {
        BookAJAXView ajaxView = new BookAJAXView();
        convert(bookWebService.get(id), ajaxView);
        return ajaxView;
    }

    public SearchBookAJAXResponse search(SearchBookAJAXRequest request) {
        SearchBookRequest boRequest = new SearchBookRequest();
        convert(request, boRequest);
        SearchBookAJAXResponse ajaxResponse = new SearchBookAJAXResponse();
        convert(bookWebService.search(boRequest), ajaxResponse);
        return ajaxResponse;
    }

    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest ajaxRequest) {
        CreateReservationRequest request = new CreateReservationRequest();
        convert(ajaxRequest, request);
        CreateReservationResponse response = bookWebService.reserve(request);
        CreateReservationAJAXResponse ajaxResponse = new CreateReservationAJAXResponse();
        convert(response, ajaxResponse);
        return ajaxResponse;
    }

    public SearchRecordByUserIdAJAXResponse searchRecordByUserId(Long userId) {
        SearchRecordResponse response = bookWebService.searchRecordByUserId(userId);
        SearchRecordByUserIdAJAXResponse ajaxResponse = new SearchRecordByUserIdAJAXResponse();
        convert(response, ajaxResponse);
        return ajaxResponse;
    }

    public BorrowBookAJAXResponse borrowBook(BorrowBookAJAXRequest ajaxRequest) {
        BorrowBookRequest request = new BorrowBookRequest();
        convert(ajaxRequest, request);
        BorrowBookResponse response = bookWebService.borrowBook(request);
        BorrowBookAJAXResponse ajaxResponse = new BorrowBookAJAXResponse();
        convert(response, ajaxResponse);
        return ajaxResponse;
    }

    public ReturnBookAJAXResponse returnBook(ReturnBookAJAXRequest ajaxRequest) {
        ReturnBookRequest request = new ReturnBookRequest();
        convert(ajaxRequest, request);
        ReturnBookResponse response = bookWebService.returnBook(request);
        ReturnBookAJAXResponse ajaxResponse = new ReturnBookAJAXResponse();
        convert(response, ajaxResponse);
        return ajaxResponse;
    }

    private void convert(ReturnBookResponse response, ReturnBookAJAXResponse ajaxResponse) {
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.returnTime = response.returnTime;
    }

    private void convert(BookView bookView, BookAJAXView ajaxView) {
        ajaxView.id = bookView.id;
        ajaxView.name = bookView.name;
        ajaxView.categoryName = bookView.categoryName;
        ajaxView.authorName = bookView.authorName;
        ajaxView.tagName = bookView.tagName;
        ajaxView.description = bookView.description;
        ajaxView.pub = bookView.pub;
        ajaxView.num = bookView.num;
    }

    private void convert(ReturnBookAJAXRequest ajaxRequest, ReturnBookRequest request) {
        request.userId = ajaxRequest.userId;
        request.bookId = ajaxRequest.bookId;
    }

    private void convert(BorrowBookResponse response, BorrowBookAJAXResponse ajaxResponse) {
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.borrowTime = response.borrowTime;
        ajaxResponse.returnTime = response.returnTime;
    }

    private void convert(BorrowBookAJAXRequest ajaxRequest, BorrowBookRequest request) {
        request.userId = ajaxRequest.userId;
        request.bookId = ajaxRequest.bookId;
        request.returnTime = ajaxRequest.returnTime;
    }

    private void convert(SearchRecordResponse response, SearchRecordByUserIdAJAXResponse ajaxResponse) {
        ajaxResponse.borrowedRecords = response.borrowedRecords.stream().map(this::convert).collect(Collectors.toList());
        ajaxResponse.total = response.total;
    }

    private void convert(CreateReservationResponse response, CreateReservationAJAXResponse ajaxResponse) {
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
    }

    private void convert(SearchBookAJAXRequest ajaxRequest, SearchBookRequest request) {
        request.skip = ajaxRequest.skip;
        request.limit = ajaxRequest.limit;
        request.name = ajaxRequest.name;
        request.pub = ajaxRequest.pub;
        request.category = ajaxRequest.category;
        request.tag = ajaxRequest.tag;
        request.author = ajaxRequest.author;
        request.description = ajaxRequest.description;
    }

    private void convert(SearchBookResponse boResponse, SearchBookAJAXResponse ajaxResponse) {
        ajaxResponse.books = boResponse.books.stream().map(boBookView -> {
            BookAJAXView bookAJAXView = new BookAJAXView();
            bookAJAXView.id = boBookView.id;
            bookAJAXView.name = boBookView.name;
            bookAJAXView.categoryName = boBookView.categoryName;
            bookAJAXView.authorName = boBookView.authorName;
            bookAJAXView.tagName = boBookView.tagName;
            bookAJAXView.description = boBookView.description;
            bookAJAXView.pub = boBookView.pub;
            bookAJAXView.num = boBookView.num;
            return bookAJAXView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boResponse.total;
    }

    private void convert(CreateReservationAJAXRequest ajaxRequest, CreateReservationRequest request) {
        request.userId = ajaxRequest.userId;
        request.bookId = ajaxRequest.bookId;
    }

    private GetBorrowedRecordAJAXResponse convert(BorrowedRecordView response) {
        GetBorrowedRecordAJAXResponse ajaxResponse = new GetBorrowedRecordAJAXResponse();
        ajaxResponse.id = response.id;
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.borrowTime = response.borrowTime;
        ajaxResponse.returnTime = response.returnTime;
        ajaxResponse.isReturned = response.isReturned;
        return ajaxResponse;
    }
}