package app;

import app.book.api.BOBookWebService;
import app.book.api.BookWebService;
import app.book.api.kafka.BorrowedRecordExpirationMessage;
import app.book.api.kafka.ReservationAvailabilityMessage;
import app.book.domain.Book;
import app.book.domain.BookView;
import app.book.domain.BorrowedRecord;
import app.book.domain.Reservation;
import app.book.service.BOBookService;
import app.book.service.BookService;
import app.book.service.ReservationService;
import app.book.web.BOBookWebServiceImpl;
import app.book.web.BookWebServiceImpl;
import app.job.RecordExpirationNotificationJob;
import app.user.api.BOUserWebService;
import core.framework.module.APIConfig;
import core.framework.module.DBConfig;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

import java.time.Duration;

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
        configureKafka();


        APIConfig api = api();
        api.client(BOUserWebService.class, requiredProperty("app.user.serviceURL"));

        bind(ReservationService.class);
        bind(BookService.class);
        bind(BOBookService.class);

        api.service(BookWebService.class, bind(BookWebServiceImpl.class));
        api.service(BOBookWebService.class, bind(BOBookWebServiceImpl.class));

        schedule().fixedRate("record-expiration-notify-job", bind(RecordExpirationNotificationJob.class), Duration.ofDays(1));
    }

    private void mongoConfig() {
        MongoConfig config = config(MongoConfig.class);
        config.uri(requiredProperty("sys.mongo.uri"));
        config.collection(BorrowedRecord.class);
    }

    private void configureKafka() {
        kafka().publish("reservation-availability", ReservationAvailabilityMessage.class);
        kafka().publish("borrowed-record-expiration", BorrowedRecordExpirationMessage.class);
    }
}
