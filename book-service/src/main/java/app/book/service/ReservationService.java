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
import java.util.Optional;

/**
 * @author Ethan
 */
public class ReservationService {
    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);
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
            Optional<Book> bookOptional = bookRepository.get(reservation.bookId);
            if (bookOptional.isEmpty()) {
                throw new NotFoundException("book not found.");
            }
            Book book = bookOptional.get();
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
            Optional<Book> bookOptional = bookRepository.get(reservation.bookId);
            if (bookOptional.isEmpty()) {
                throw new NotFoundException("book not found.");
            }
            if (ZonedDateTime.now().isAfter(reservation.reserveTime.plusDays(7))) {
                reservationRepository.delete(reservation.id);
                return;
            }
            if (reservation.reserveTime.plusDays(6).getDayOfMonth() == ZonedDateTime.now().getDayOfMonth()) {
                logger.info("publish expirationMessage, userId={}, bookId={}", reservation.userId, reservation.bookId);
                ExpirationMessage message = new ExpirationMessage();
                message.bookId = reservation.bookId;
                message.userId = reservation.userId;
                expirationMessagePublisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
