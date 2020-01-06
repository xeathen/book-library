package app.tag.service;

import app.ErrorCodes;
import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOCreateTagResponse;
import app.book.api.tag.BOListTagResponse;
import app.book.api.tag.TagView;
import app.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOTagService {
    @Inject
    Repository<Tag> tagRepository;

    public BOListTagResponse list() {
        BOListTagResponse response = new BOListTagResponse();
        Query<Tag> query = tagRepository.select();
        response.tags = query.fetch().stream().map(tag -> {
            TagView tagView = new TagView();
            tagView.id = tag.id;
            tagView.name = tag.name;
            return tagView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateTagResponse create(BOCreateTagRequest request) {
        if (Strings.isBlank(request.name)) {
            throw new BadRequestException("tag name must be not null", ErrorCodes.NULL_TAG);
        }
        BOCreateTagResponse response = new BOCreateTagResponse();
        Tag tag = new Tag();
        tag.name = request.name;
        response.id = (int) tagRepository.insert(tag).orElseThrow();
        response.name = request.name;
        return response;
    }
}
