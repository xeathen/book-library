package app.job;

import app.book.api.kafka.ExpireMessage;
import app.book.domain.Book;
import app.book.domain.Reservation;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.log.ActionLogContext;
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
    public void execute(JobContext context) throws Exception {
        reservationRepository.select().fetch().forEach(reservation -> {
            ActionLogContext.put("reservation_id", reservation.id);
            ActionLogContext.put("user_id", reservation.userId);
            ActionLogContext.put("book_id", reservation.bookId);
            Optional<Book> bookOptional = bookRepository.get(reservation.bookId);
            if (bookOptional.isEmpty()) {
                throw new NotFoundException("book not found.");
            }
            if (reservation.reserveTime.plusDays(6).getDayOfMonth() == ZonedDateTime.now().getDayOfMonth()) {
                ExpireMessage message = new ExpireMessage();
                message.bookId = reservation.bookId;
                message.userId = reservation.userId;
                messagePublisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
