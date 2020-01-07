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
import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.GetBookAJAXResponse;
import app.web.api.book.ReturnBackBookAJAXResponse;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXRequest;
import app.web.api.book.SearchRecordAJAXResponse;
import app.web.api.book.SearchRecordAJAXResponse.BorrowedRecord;
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

    public BorrowBookAJAXResponse borrow(Long userId, Long bookId, BorrowBookAJAXRequest ajaxRequest) {
        BorrowBookRequest request = borrowBookRequest(userId, ajaxRequest);
        BorrowBookResponse response = bookWebService.borrow(bookId, request);
        return borrowBookAJAXResponse(response);
    }

    public ReturnBackBookAJAXResponse returnBack(Long userId, Long bookId) {
        ReturnBackBookRequest request = returnBackBookRequest(userId);
        ReturnBackBookResponse response = bookWebService.returnBack(bookId, request);
        return returnBookAJAXResponse(response);
    }

    public CreateReservationAJAXResponse reserve(Long userId, Long bookId) {
        CreateReservationRequest request = createReservationRequest(userId);
        CreateReservationResponse reserve = bookWebService.reserve(bookId, request);
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
        ajaxResponse.quantity = response.quantity;
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
            SearchBookAJAXResponse.Book book = new SearchBookAJAXResponse.Book();
            book.id = boBookView.id;
            book.name = boBookView.name;
            book.categoryName = boBookView.categoryName;
            book.authorName = boBookView.authorName;
            book.tagName = boBookView.tagName;
            book.publishingHouse = boBookView.publishingHouse;
            book.description = boBookView.description;
            book.quantity = boBookView.quantity;
            return book;
        }).collect(Collectors.toList());
        ajaxResponse.total = boResponse.total;
        return ajaxResponse;
    }

    private BorrowBookRequest borrowBookRequest(Long userId, BorrowBookAJAXRequest ajaxRequest) {
        BorrowBookRequest request = new BorrowBookRequest();
        request.userId = userId;
        request.expectedReturnTime = ajaxRequest.expectedReturnTime;
        return request;
    }

    private BorrowBookAJAXResponse borrowBookAJAXResponse(BorrowBookResponse response) {
        BorrowBookAJAXResponse ajaxResponse = new BorrowBookAJAXResponse();
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.borrowTime = response.borrowTime;
        ajaxResponse.expectedReturnTime = response.expectedReturnTime;
        return ajaxResponse;
    }

    private ReturnBackBookRequest returnBackBookRequest(Long userId) {
        ReturnBackBookRequest request = new ReturnBackBookRequest();
        request.userId = userId;
        return request;
    }

    private ReturnBackBookAJAXResponse returnBookAJAXResponse(ReturnBackBookResponse response) {
        ReturnBackBookAJAXResponse ajaxResponse = new ReturnBackBookAJAXResponse();
        ajaxResponse.actualReturnTime = response.actualReturnTime;
        return ajaxResponse;
    }

    private CreateReservationRequest createReservationRequest(Long userId) {
        CreateReservationRequest request = new CreateReservationRequest();
        request.userId = userId;
        return request;
    }

    private CreateReservationAJAXResponse createReservationAJAXResponse(CreateReservationResponse response) {
        CreateReservationAJAXResponse ajaxResponse = new CreateReservationAJAXResponse();
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
        ajaxResponse.borrowedRecords = response.borrowedRecords.stream().map(this::borrowedRecord).collect(Collectors.toList());
        ajaxResponse.total = response.total;
        return ajaxResponse;
    }

    private BorrowedRecord borrowedRecord(BorrowedRecordView response) {
        BorrowedRecord borrowedRecord = new BorrowedRecord();
        borrowedRecord.id = response.id;
        borrowedRecord.userId = response.userId;
        borrowedRecord.userName = response.userName;
        borrowedRecord.bookId = response.bookId;
        borrowedRecord.bookName = response.bookName;
        borrowedRecord.borrowTime = response.borrowTime;
        borrowedRecord.expectedReturnTime = response.expectedReturnTime;
        borrowedRecord.actualReturnTime = response.actualReturnTime;
        return borrowedRecord;
    }
}
