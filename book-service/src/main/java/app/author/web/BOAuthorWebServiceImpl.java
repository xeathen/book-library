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
    BOAuthorService boAuthorService;

    @Override
    public BOCreateAuthorResponse create(BOCreateAuthorRequest request) {
        ActionLogContext.put("authorName", request.name);
        return boAuthorService.create(request);
    }

    @Override
    public BOListAuthorResponse list() {
        return boAuthorService.list();
    }
}
