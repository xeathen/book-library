package app.author.service;

import app.author.domain.Author;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOCreateAuthorResponse;
import app.book.api.author.BOListAuthorResponse;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOAuthorService {
    @Inject
    Repository<Author> authorRepository;

    public BOListAuthorResponse list() {
        Query<Author> query = authorRepository.select();
        BOListAuthorResponse response = new BOListAuthorResponse();
        response.authors = query.fetch().stream().map(author -> {
            BOListAuthorResponse.Author authorView = new BOListAuthorResponse.Author();
            authorView.id = author.id;
            authorView.name = author.name;
            return authorView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }

    public BOCreateAuthorResponse create(BOCreateAuthorRequest request) {
        Author author = new Author();
        author.name = request.name;
        long id = authorRepository.insert(author).orElseThrow();
        BOCreateAuthorResponse response = new BOCreateAuthorResponse();
        response.id = (int) id;
        response.name = request.name;
        return response;
    }
}
