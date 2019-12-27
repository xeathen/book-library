package app.job;

import app.book.api.kafka.ReservationMessage;
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
public class AvailabilityNotifyJob implements Job {
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    Repository<Book> bookRepository;
    @Inject
    MessagePublisher<ReservationMessage> publisher;

    @Override
    public void execute(JobContext context) {
        reservationRepository.select().fetch().forEach(reservation -> {
            Optional<Book> bookOptional = bookRepository.get(reservation.bookId);
            if (bookOptional.isEmpty()) {
                throw new NotFoundException("book not found.");
            }
            if (ZonedDateTime.now().isAfter(reservation.reserveTime.plusDays(7))){
                reservationRepository.delete(reservation.id);
                return;
            }
            Book book = bookOptional.get();
            if (book.num > 0) {
                ReservationMessage message = new ReservationMessage();
                message.userId = reservation.userId;
                message.bookId = reservation.bookId;
                publisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
