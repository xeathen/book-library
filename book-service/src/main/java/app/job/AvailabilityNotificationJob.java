package app.job;

import app.book.service.ReservationService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;

/**
 * @author Ethan
 */
public class AvailabilityNotificationJob implements Job {
    @Inject
    ReservationService reservationService;

    @Override
    public void execute(JobContext context) {
        ActionLogContext.put("jobName", context.name);
        ActionLogContext.put("jobScheduleTime", context.scheduledTime);
        reservationService.notifyAvailability();
    }
}
