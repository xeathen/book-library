package app.job;

import app.book.api.kafka.ReservationMessage;
import app.book.domain.Book;
import app.book.domain.Reservation;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.log.ActionLogContext;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Ethan
 */
public class AvailabilityNotifyJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(AvailabilityNotifyJob.class);
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    Repository<Book> bookRepository;
    @Inject
    MessagePublisher<ReservationMessage> publisher;

    @Override
    public void execute(JobContext context) {
        reservationRepository.select().fetch().forEach(reservation -> {
            ActionLogContext.put("reservation_id", reservation.id);
            ActionLogContext.put("user_id", reservation.userId);
            ActionLogContext.put("book_id", reservation.bookId);
            Optional<Book> bookOptional = bookRepository.get(reservation.bookId);
            if (bookOptional.isEmpty()) {
                throw new NotFoundException("book not found.");
            }
            Book book = bookOptional.get();
            if (book.num > 0) {
                logger.info("publish message, userId={}, bookId={}", reservation.userId, reservation.bookId);
                ReservationMessage message = new ReservationMessage();
                message.userId = reservation.userId;
                message.bookId = reservation.bookId;
                publisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
