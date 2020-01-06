package app.web.book.service;

import app.book.api.BookWebService;
import app.book.api.book.BorrowBookRequest;
import app.book.api.book.BorrowBookResponse;
import app.book.api.book.BorrowedRecordView;
import app.book.api.book.CreateReservationRequest;
import app.book.api.book.CreateReservationResponse;
import app.book.api.book.GetBookResponse;
import app.book.api.book.ReturnBookRequest;
import app.book.api.book.ReturnBookResponse;
import app.book.api.book.SearchBookRequest;
import app.book.api.book.SearchBookResponse;
import app.book.api.book.SearchRecordRequest;
import app.book.api.book.SearchRecordResponse;
import app.user.api.BOUserWebService;
import app.web.api.book.BookView;
import app.web.api.book.BorrowBookAJAXRequest;
import app.web.api.book.BorrowBookAJAXResponse;
import app.web.api.book.BorrowedRecordAJAXView;
import app.web.api.book.CreateReservationAJAXRequest;
import app.web.api.book.CreateReservationAJAXResponse;
import app.web.api.book.GetBookAJAXResponse;
import app.web.api.book.ReturnBookAJAXRequest;
import app.web.api.book.ReturnBookAJAXResponse;
import app.web.api.book.SearchBookAJAXRequest;
import app.web.api.book.SearchBookAJAXResponse;
import app.web.api.book.SearchRecordAJAXRequest;
import app.web.api.book.SearchRecordAJAXResponse;
import core.framework.inject.Inject;
import core.framework.web.WebContext;
import core.framework.web.exception.ForbiddenException;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BookService {
    @Inject
    BookWebService bookWebService;
    @Inject
    BOUserWebService boUserWebService;
    @Inject
    WebContext webContext;

    public GetBookAJAXResponse get(Long id) {
        return getBookAJAXResponse(bookWebService.get(id));
    }

    public SearchBookAJAXResponse search(SearchBookAJAXRequest ajaxRequest) {
        SearchBookRequest request = searchBookRequest(ajaxRequest);
        SearchBookResponse response = bookWebService.search(request);
        return searchBookAJAXResponse(response);
    }

    public BorrowBookAJAXResponse borrow(BorrowBookAJAXRequest ajaxRequest) {
        checkUser(ajaxRequest.userId);
        BorrowBookRequest request = borrowBookRequest(ajaxRequest);
        BorrowBookResponse response = bookWebService.borrow(request);
        return borrowBookAJAXResponse(response);
    }

    public ReturnBookAJAXResponse returnBack(ReturnBookAJAXRequest ajaxRequest) {
        checkUser(ajaxRequest.userId);
        ReturnBookRequest request = returnBookRequest(ajaxRequest);
        ReturnBookResponse response = bookWebService.returnBack(request);
        return returnBookAJAXResponse(response);
    }

    public CreateReservationAJAXResponse reserve(CreateReservationAJAXRequest ajaxRequest) {
        checkUser(ajaxRequest.userId);
        CreateReservationRequest request = createReservationRequest(ajaxRequest);
        CreateReservationResponse reserve = bookWebService.reserve(request);
        return createReservationAJAXResponse(reserve);
    }

    public SearchRecordAJAXResponse searchRecord(SearchRecordAJAXRequest ajaxRequest, String userName) {
        SearchRecordRequest request = searchRecordRequest(ajaxRequest, userName);
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
        ajaxResponse.mount = response.mount;
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
            bookView.mount = boBookView.mount;
            return bookView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boResponse.total;
        return ajaxResponse;
    }

    private BorrowBookRequest borrowBookRequest(BorrowBookAJAXRequest ajaxRequest) {
        BorrowBookRequest request = new BorrowBookRequest();
        request.userId = ajaxRequest.userId;
        request.bookId = ajaxRequest.bookId;
        request.returnTime = ajaxRequest.returnTime;
        return request;
    }

    private BorrowBookAJAXResponse borrowBookAJAXResponse(BorrowBookResponse response) {
        BorrowBookAJAXResponse ajaxResponse = new BorrowBookAJAXResponse();
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.borrowTime = response.borrowTime;
        ajaxResponse.returnTime = response.returnTime;
        return ajaxResponse;
    }

    private ReturnBookRequest returnBookRequest(ReturnBookAJAXRequest ajaxRequest) {
        ReturnBookRequest request = new ReturnBookRequest();
        request.userId = ajaxRequest.userId;
        request.bookId = ajaxRequest.bookId;
        return request;
    }

    private ReturnBookAJAXResponse returnBookAJAXResponse(ReturnBookResponse response) {
        ReturnBookAJAXResponse ajaxResponse = new ReturnBookAJAXResponse();
        ajaxResponse.userId = response.userId;
        ajaxResponse.userName = response.userName;
        ajaxResponse.bookId = response.bookId;
        ajaxResponse.bookName = response.bookName;
        ajaxResponse.returnTime = response.returnTime;
        return ajaxResponse;
    }

    private CreateReservationRequest createReservationRequest(CreateReservationAJAXRequest ajaxRequest) {
        CreateReservationRequest request = new CreateReservationRequest();
        request.userId = ajaxRequest.userId;
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

    private SearchRecordRequest searchRecordRequest(SearchRecordAJAXRequest ajaxRequest, String userName) {
        SearchRecordRequest request = new SearchRecordRequest();
        request.skip = ajaxRequest.skip;
        request.limit = ajaxRequest.limit;
        request.userName = userName;
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
        ajaxResponse.returnTime = response.returnTime;
        ajaxResponse.isReturned = response.isReturned;
        return ajaxResponse;
    }

    private void checkUser(Long userId) {
        String requestUserName = boUserWebService.get(userId).userName;
        String userName = webContext.request().session().get("userName").orElseThrow();
        if (!userName.equals(requestUserName)) {
            throw new ForbiddenException("Incorrect user.");
        }
    }
}
