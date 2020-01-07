package app.book.service;

import app.book.api.kafka.ExpirationMessage;
import app.book.api.kafka.ReservationMessage;
import app.book.domain.Book;
import app.book.domain.Reservation;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
public class ReservationService {
    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    public Integer expiredDays;
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    Repository<Book> bookRepository;
    @Inject
    MessagePublisher<ReservationMessage> reservationMessagePublisher;
    @Inject
    MessagePublisher<ExpirationMessage> expirationMessagePublisher;

    public void notifyAvailability() {
        reservationRepository.select().fetch().forEach(reservation -> {
            Book book = bookRepository.get(reservation.bookId).orElseThrow(() -> new NotFoundException("Book not found, id=" + reservation.bookId));
            if (book.quantity > 0) {
                logger.info("publish reservationMessage, userId={}, bookId={}", reservation.userId, reservation.bookId);
                ReservationMessage message = new ReservationMessage();
                message.userId = reservation.userId;
                message.bookId = reservation.bookId;
                reservationMessagePublisher.publish(reservation.id.toString(), message);
                reservationRepository.delete(reservation.id);
            }
        });
    }

    public void notifyExpiration() {
        reservationRepository.select().fetch().forEach(reservation -> {
            bookRepository.get(reservation.bookId).orElseThrow(() -> new NotFoundException("Book not found, id=" + reservation.bookId));
            ZonedDateTime expiredTime = reservation.reserveTime.plusDays(expiredDays);
            if (ZonedDateTime.now().isAfter(expiredTime)) {
                reservationRepository.delete(reservation.id);
                return;
            }
            if (expiredTime.minusDays(1).getDayOfMonth() == ZonedDateTime.now().getDayOfMonth()) {
                logger.info("publish expirationMessage, userId={}, bookId={}", reservation.userId, reservation.bookId);
                ExpirationMessage message = new ExpirationMessage();
                message.bookId = reservation.bookId;
                message.userId = reservation.userId;
                expirationMessagePublisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
