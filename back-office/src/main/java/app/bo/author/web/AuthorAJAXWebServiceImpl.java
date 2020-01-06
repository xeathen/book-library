package app.bo.author.web;

import app.bo.api.AuthorAJAXWebService;
import app.bo.api.author.CreateAuthorAJAXRequest;
import app.bo.api.author.CreateAuthorAJAXResponse;
import app.bo.api.author.ListAuthorAJAXResponse;
import app.bo.author.service.AuthorService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class AuthorAJAXWebServiceImpl implements AuthorAJAXWebService {
    @Inject
    AuthorService authorService;

    @Override
    public CreateAuthorAJAXResponse create(CreateAuthorAJAXRequest request) {
        ActionLogContext.put("authorName", request.name);
        return authorService.create(request);
    }

    @Override
    public ListAuthorAJAXResponse list() {
        return authorService.list();
    }
}
