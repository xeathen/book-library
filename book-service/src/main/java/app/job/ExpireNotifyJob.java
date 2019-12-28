package app.job;

import app.book.api.kafka.ExpireMessage;
import app.book.domain.Book;
import app.book.domain.Reservation;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import core.framework.web.exception.NotFoundException;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author Ethan
 */
public class ExpireNotifyJob implements Job {
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    MessagePublisher<ExpireMessage> messagePublisher;
    @Inject
    Repository<Book> bookRepository;

    @Override
    public void execute(JobContext context) throws NotFoundException {
        //        ActionLogContext.put();
        reservationRepository.select().fetch().forEach(reservation -> {
            Optional<Book> bookOptional = bookRepository.get(reservation.bookId);
            if (bookOptional.isEmpty()) {
                throw new NotFoundException("book not found.");
            }
            if (reservation.reserveTime.plusDays(6).getDayOfMonth() == ZonedDateTime.now().getDayOfMonth()) {
                System.out.println("I'm in");
                ExpireMessage message = new ExpireMessage();
                message.bookId = reservation.bookId;
                message.userId = reservation.userId;
                messagePublisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
