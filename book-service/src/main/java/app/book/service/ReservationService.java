package app.book.service;

import app.book.api.kafka.ReservationAvailabilityMessage;
import app.book.domain.Reservation;
import app.user.api.BOUserWebService;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.UserStatusView;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ethan
 */
public class ReservationService {
    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    BOUserWebService boUserWebService;
    @Inject
    MessagePublisher<ReservationAvailabilityMessage> reservationMessagePublisher;

    public void notifyReservationAvailability(Long bookId, String bookName) {
        Query<Reservation> reservationQuery = reservationRepository.select();
        reservationQuery.where("book_id = ?", bookId);
        reservationQuery.fetch().forEach(reservation -> {
            logger.info("publish reservationAvailabilityMessage, userId={}, bookId={}", reservation.userId, reservation.bookId);
            reservationRepository.delete(reservation.id);
            BOGetUserResponse boGetUserResponse = boUserWebService.get(reservation.userId);
            if (boGetUserResponse.status == UserStatusView.ACTIVE) {
                ReservationAvailabilityMessage message = new ReservationAvailabilityMessage();
                message.username = boGetUserResponse.username;
                message.email = boGetUserResponse.email;
                message.bookName = bookName;
                reservationMessagePublisher.publish(reservation.id.toString(), message);
            }
        });
    }
}
