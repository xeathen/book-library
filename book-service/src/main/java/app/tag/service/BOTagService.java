package app.tag.service;

import app.book.api.tag.BOCreateTagRequest;
import app.book.api.tag.BOCreateTagResponse;
import app.book.api.tag.BOListTagResponse;
import app.tag.domain.Tag;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOTagService {
    @Inject
    Repository<Tag> tagRepository;

    public BOListTagResponse list() {
        Query<Tag> query = tagRepository.select();
        BOListTagResponse response = new BOListTagResponse();
        response.tags = query.fetch().stream().map(tag -> {
            BOListTagResponse.Tag tagView = new BOListTagResponse.Tag();
            tagView.id = tag.id;
            tagView.name = tag.name;
            return tagView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateTagResponse create(BOCreateTagRequest request) {
        Tag tag = new Tag();
        tag.name = request.name;
        long id = tagRepository.insert(tag).orElseThrow();
        BOCreateTagResponse response = new BOCreateTagResponse();
        response.id = (int) id;
        response.name = request.name;
        return response;
    }
}
