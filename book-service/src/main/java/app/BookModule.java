package app;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.domain.Author;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Category;
import app.book.domain.Reservation;
import app.book.domain.Tag;
import app.book.service.BOBookService;
import app.book.service.BookService;
import app.book.web.BOBookWebServiceImpl;
import app.book.web.BookWebServiceImpl;
import app.user.api.UserWebService;
import core.framework.module.APIConfig;
import core.framework.module.DBConfig;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

/**
 * @author Ethan
 */
public class BookModule extends Module {
    @Override
    protected void initialize() {
        DBConfig db = db();
        db.repository(Book.class);
        db.repository(Category.class);
        db.repository(Tag.class);
        db.repository(Author.class);
        db.repository(Reservation.class);

        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowedRecord.class);


        APIConfig api = api();
        api.client(UserWebService.class, requiredProperty("app.userWebService.URL"));

        bind(BOBookService.class);
        bind(BookService.class);
        api.service(BookWebService.class, bind(BookWebServiceImpl.class));
        api.service(BOBookWebService.class, bind(BOBookWebServiceImpl.class));
    }
}
