package app.bo.tag.service;

import app.bo.api.tag.CreateTagAJAXRequest;
import app.bo.api.tag.CreateTagAJAXResponse;
import app.bo.api.tag.ListTagAJAXResponse;
import app.book.api.BOTagWebService;
import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOCreateTagResponse;
import app.book.api.tag.BOListTagResponse;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class TagService {
    @Inject
    BOTagWebService boTagWebService;

    public ListTagAJAXResponse list() {
        ListTagAJAXResponse ajaxResponse = new ListTagAJAXResponse();
        BOListTagResponse boListTagResponse = boTagWebService.list();
        ajaxResponse.tags = boListTagResponse.tags.stream().map(boTagView -> {
            ListTagAJAXResponse.Tag tagView = new ListTagAJAXResponse.Tag();
            tagView.id = boTagView.id;
            tagView.name = boTagView.name;
            return tagView;
        }).collect(Collectors.toList());
        ajaxResponse.total = boListTagResponse.total;
        return ajaxResponse;
    }

    public CreateTagAJAXResponse create(CreateTagAJAXRequest ajaxRequest) {
        BOCreateTagRequest boRequest = new BOCreateTagRequest();
        boRequest.name = ajaxRequest.name;
        return createTagAJAXResponse(boTagWebService.create(boRequest));
    }

    private CreateTagAJAXResponse createTagAJAXResponse(BOCreateTagResponse boResponse) {
        CreateTagAJAXResponse ajaxResponse = new CreateTagAJAXResponse();
        ajaxResponse.id = boResponse.id;
        ajaxResponse.name = boResponse.name;
        return ajaxResponse;
    }
}
