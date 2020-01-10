package app.job;

import app.book.service.BookService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;

/**
 * @author Ethan
 */
public class RecordExpirationNotificationJob implements Job {
    @Inject
    BookService bookService;

    @Override
    public void execute(JobContext context) {
        ActionLogContext.put("jobName", context.name);
        ActionLogContext.put("jobScheduleTime", context.scheduledTime);
        bookService.notifyRecordExpiration();
    }
}
