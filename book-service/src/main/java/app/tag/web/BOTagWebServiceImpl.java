package app.tag.web;

import app.book.api.BOTagWebService;
import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOCreateTagResponse;
import app.book.api.tag.BOListTagResponse;
import app.tag.service.BOTagService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class BOTagWebServiceImpl implements BOTagWebService {
    @Inject
    BOTagService boTagWebService;

    @Override
    public BOListTagResponse list() {
        return boTagWebService.list();
    }

    @Override
    public BOCreateTagResponse create(BOCreateTagRequest request) {
        ActionLogContext.put("tagName", request.tagName);
        return boTagWebService.create(request);
    }
}
