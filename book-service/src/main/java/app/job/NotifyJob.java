package app.job;

import app.book.api.kafka.ReservationMessage;
import app.book.domain.Reservation;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Ethan
 */
public class NotifyJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(NotifyJob.class);
    @Inject
    Repository<Reservation> reservationRepository;
    @Inject
    MessagePublisher<ReservationMessage> publisher;


    @Override

    public void execute(JobContext context) throws Exception {
        reservationRepository.select().fetch().forEach(reservation ->{
            ReservationMessage message = new ReservationMessage();
            message.userId = reservation.userId;
            message.bookId = reservation.bookId;
            publisher.publish(reservation.id.toString(), message);
        });
    }
}
