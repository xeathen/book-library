package app.bo.tag.web;

import app.bo.api.TagAJAXWebService;
import app.bo.api.tag.CreateTagAJAXRequest;
import app.bo.api.tag.CreateTagAJAXResponse;
import app.bo.api.tag.ListTagAJAXResponse;
import app.bo.tag.service.TagService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author Ethan
 */
public class TagAJAXWebServiceImpl implements TagAJAXWebService {
    @Inject
    TagService tagService;

    @Override
    public CreateTagAJAXResponse create(CreateTagAJAXRequest request) {
        ActionLogContext.put("tagName", request.tagName);
        return tagService.create(request);
    }

    @Override
    public ListTagAJAXResponse list() {
        return tagService.list();
    }
}
