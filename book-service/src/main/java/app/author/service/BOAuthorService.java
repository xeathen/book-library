package app.author.service;

import app.ErrorCodes;
import app.author.domain.Author;
import app.book.api.author.AuthorView;
import app.book.api.author.BOCreateAuthorRequest;
import app.book.api.author.BOCreateAuthorResponse;
import app.book.api.author.BOListAuthorResponse;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;

import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BOAuthorService {
    @Inject
    Repository<Author> authorRepository;

    public BOCreateAuthorResponse create(BOCreateAuthorRequest request) {
        if (Strings.isBlank(request.name)) {
            throw new BadRequestException("author name must be not null", ErrorCodes.NULL_AUTHOR);
        }
        BOCreateAuthorResponse response = new BOCreateAuthorResponse();
        Author author = new Author();
        author.name = request.name;
        response.id = (int) authorRepository.insert(author).orElseThrow();
        response.name = request.name;
        return response;
    }

    public BOListAuthorResponse list() {
        BOListAuthorResponse response = new BOListAuthorResponse();
        Query<Author> query = authorRepository.select();
        response.authors = query.fetch().stream().map(author -> {
            AuthorView authorView = new AuthorView();
            authorView.id = author.id;
            authorView.name = author.name;
            return authorView;
        }).collect(Collectors.toList());
        response.total = query.count();
        return response;
    }
}
