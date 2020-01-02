package app.bo.author.service;

import app.bo.api.author.AuthorView;
import app.bo.api.author.CreateAuthorAJAXRequest;
import app.bo.api.author.CreateAuthorAJAXResponse;
import app.bo.api.author.ListAuthorAJAXResponse;
import app.book.api.BOAuthorWebService;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOCreateAuthorResponse;
import app.book.api.author.BOListAuthorResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class AuthorService {
    @Inject
    BOAuthorWebService boAuthorWebService;

    public ListAuthorAJAXResponse list() {
        ListAuthorAJAXResponse ajaxResponse = new ListAuthorAJAXResponse();
        BOListAuthorResponse boListAuthorResponse = boAuthorWebService.list();
        ajaxResponse.authors = boListAuthorResponse.authors.stream().map(boAuthorView -> {
            AuthorView authorView = new AuthorView();
            authorView.authorId = boAuthorView.authorId;
            authorView.authorName = boAuthorView.authorName;
            return authorView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListAuthorResponse.total;
        return ajaxResponse;
    }

    public CreateAuthorAJAXResponse create(CreateAuthorAJAXRequest ajaxRequest) {
        BOCreateAuthorRequest boRequest = new BOCreateAuthorRequest();
        boRequest.authorName = ajaxRequest.authorName;
        return createAuthorAJAXResponse(boAuthorWebService.create(boRequest));
    }

    private CreateAuthorAJAXResponse createAuthorAJAXResponse(BOCreateAuthorResponse boResponse) {
        CreateAuthorAJAXResponse ajaxResponse = new CreateAuthorAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.authorName = boResponse.authorName;
        return ajaxResponse;
    }
}
