package app.author.web;

import app.author.service.BOAuthorService;
import app.book.api.BOAuthorWebService;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOCreateAuthorResponse;
import app.book.api.author.BOListAuthorResponse;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BOAuthorWebServiceImpl implements BOAuthorWebService {
    @Inject
    BOAuthorService boAuthorWebService;

    @Override
    public BOCreateAuthorResponse create(BOCreateAuthorRequest request) {
        ActionLogContext.put("authorName", request.authorName);
        return boAuthorWebService.create(request);
    }

    @Override
    public BOListAuthorResponse list() {
        return boAuthorWebService.list();
    }
}
