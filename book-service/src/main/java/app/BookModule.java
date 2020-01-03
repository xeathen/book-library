package app;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.api.book.BookView;
import app.book.domain.Book;
import app.book.domain.BorrowedRecord;
import app.book.domain.Reservation;
import app.book.service.BOBookService;
import app.book.service.BookService;
import app.book.web.BOBookWebServiceImpl;
import app.book.web.BookWebServiceImpl;
import app.user.api.BOUserWebService;
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
        db.repository(Reservation.class);
        db.view(BookView.class);

        mongoConfig();

        APIConfig api = api();
        api.client(BOUserWebService.class, requiredProperty("app.user.serviceURL"));

        bind(BookService.class);
        bind(BOBookService.class);
        api.service(BookWebService.class, bind(BookWebServiceImpl.class));
        api.service(BOBookWebService.class, bind(BOBookWebServiceImpl.class));
    }

    private void mongoConfig() {
        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowedRecord.class);
    }
}
