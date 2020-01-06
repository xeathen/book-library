package app.web.book.service;

import app.book.api.BookWebService;
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
import app.web.api.book.BookView;
import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.BorrowedRecordAJAXView;
import app.web.api.book.CreateReservationAJAXRequest;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.GetBookAJAXResponse;
import app.web.api.book.ReturnBackBookAJAXRequest;
import app.web.api.book.ReturnBackBookAJAXResponse;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXRequest;
import app.web.api.book.SearchRecordAJAXResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    BookWebService bookWebService;

    public GetBookAJAXResponse get(Long id) {
        return getBookAJAXResponse(bookWebService.get(id));
    }

    public SearchBookAJAXResponse search(SearchBookAJAXRequest ajaxRequest) {
        SearchBookRequest request = searchBookRequest(ajaxRequest);
        SearchBookResponse response = bookWebService.search(request);
        return searchBookAJAXResponse(response);
    }

    public BorrowBookAJAXResponse borrow(Long userId, BorrowBookAJAXRequest ajaxRequest) {
        BorrowBookRequest request = borrowBookRequest(userId, ajaxRequest);
        BorrowBookResponse response = bookWebService.borrow(request);
        return borrowBookAJAXResponse(response);
    }

    public ReturnBackBookAJAXResponse returnBack(Long userId, ReturnBackBookAJAXRequest ajaxRequest) {
        ReturnBackBookRequest request = returnBookRequest(userId, ajaxRequest);
        ReturnBackBookResponse response = bookWebService.returnBack(request);
        return returnBookAJAXResponse(response);
    }

    public CreateReservationAJAXResponse reserve(Long userId, CreateReservationAJAXRequest ajaxRequest) {
        CreateReservationRequest request = createReservationRequest(userId, ajaxRequest);
        CreateReservationResponse reserve = bookWebService.reserve(request);
        return createReservationAJAXResponse(reserve);
    }

    public SearchRecordAJAXResponse searchRecord(Long userId, SearchRecordAJAXRequest ajaxRequest) {
        SearchRecordRequest request = searchRecordRequest(userId, ajaxRequest);
        SearchRecordResponse response = bookWebService.searchRecord(request);
        return searchRecordAJAXResponse(response);
    }

    private GetBookAJAXResponse getBookAJAXResponse(GetBookResponse response) {
        GetBookAJAXResponse ajaxResponse = new GetBookAJAXResponse();
        ajaxResponse.id = response.id;
        ajaxResponse.name = response.name;
        ajaxResponse.categoryName = response.categoryName;
        ajaxResponse.authorName = response.authorName;
        ajaxResponse.tagName = response.tagName;
        ajaxResponse.description = response.description;
        ajaxResponse.publishingHouse = response.publishingHouse;
        ajaxResponse.amount = response.amount;
        return ajaxResponse;
    }

    private SearchBookRequest searchBookRequest(SearchBookAJAXRequest ajaxRequest) {
        SearchBookRequest request = new SearchBookRequest();
        request.skip = ajaxRequest.skip;
        request.limit = ajaxRequest.limit;
        request.name = ajaxRequest.name;
        request.category = ajaxRequest.category;
        request.tag = ajaxRequest.tag;
        request.author = ajaxRequest.author;
        request.publishingHouse = ajaxRequest.publishingHouse;
        request.description = ajaxRequest.description;
        return request;
    }

    private SearchBookAJAXResponse searchBookAJAXResponse(SearchBookResponse boResponse) {
        SearchBookAJAXResponse ajaxResponse = new SearchBookAJAXResponse();
        ajaxResponse.books = boResponse.books.stream().map(boBookView -> {
            BookView bookView = new BookView();
            bookView.id = boBookView.id;
            bookView.name = boBookView.name;
            bookView.categoryName = boBookView.categoryName;
            bookView.authorName = boBookView.authorName;
            bookView.tagName = boBookView.tagName;
            bookView.publishingHouse = boBookView.publishingHouse;
            bookView.description = boBookView.description;
            bookView.amount = boBookView.amount;
            return bookView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boResponse.total;
        return ajaxResponse;
    }

    private BorrowBookRequest borrowBookRequest(Long userId, BorrowBookAJAXRequest ajaxRequest) {
        BorrowBookRequest request = new BorrowBookRequest();
        request.userId = userId;
        request.bookId = ajaxRequest.bookId;
        request.expectedReturnTime = ajaxRequest.expectedReturnTime;
        return request;
    }

    private BorrowBookAJAXResponse borrowBookAJAXResponse(BorrowBookResponse response) {
        BorrowBookAJAXResponse ajaxResponse = new BorrowBookAJAXResponse();
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.borrowTime = response.borrowTime;
        ajaxResponse.expectedReturnTime = response.expectedReturnTime;
        return ajaxResponse;
    }

    private ReturnBackBookRequest returnBookRequest(Long userId, ReturnBackBookAJAXRequest ajaxRequest) {
        ReturnBackBookRequest request = new ReturnBackBookRequest();
        request.userId = userId;
        request.bookId = ajaxRequest.bookId;
        return request;
    }

    private ReturnBackBookAJAXResponse returnBookAJAXResponse(ReturnBackBookResponse response) {
        ReturnBackBookAJAXResponse ajaxResponse = new ReturnBackBookAJAXResponse();
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.actualReturnTime = response.actualReturnTime;
        return ajaxResponse;
    }

    private CreateReservationRequest createReservationRequest(Long userId, CreateReservationAJAXRequest ajaxRequest) {
        CreateReservationRequest request = new CreateReservationRequest();
        request.userId = userId;
        request.bookId = ajaxRequest.bookId;
        return request;
    }

    private CreateReservationAJAXResponse createReservationAJAXResponse(CreateReservationResponse response) {
        CreateReservationAJAXResponse ajaxResponse = new CreateReservationAJAXResponse();
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        return ajaxResponse;
    }

    private SearchRecordRequest searchRecordRequest(Long userId, SearchRecordAJAXRequest ajaxRequest) {
        SearchRecordRequest request = new SearchRecordRequest();
        request.skip = ajaxRequest.skip;
        request.limit = ajaxRequest.limit;
        request.userId = userId;
        return request;
    }

    private SearchRecordAJAXResponse searchRecordAJAXResponse(SearchRecordResponse response) {
        SearchRecordAJAXResponse ajaxResponse = new SearchRecordAJAXResponse();
        ajaxResponse.borrowedRecords = response.borrowedRecords.stream().map(this::borrowedRecordAJAXView).collect(Collectors.toList());
        ajaxResponse.total = response.total;
        return ajaxResponse;
    }

    private BorrowedRecordAJAXView borrowedRecordAJAXView(BorrowedRecordView response) {
        BorrowedRecordAJAXView ajaxResponse = new BorrowedRecordAJAXView();
        ajaxResponse.id = response.id;
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.borrowTime = response.borrowTime;
        ajaxResponse.expectedReturnTime = response.expectedReturnTime;
        ajaxResponse.actualReturnTime = response.actualReturnTime;
        return ajaxResponse;
    }
}
