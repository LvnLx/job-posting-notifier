package com.lvnlx.job.posting.notifier.service;

import com.lvnlx.job.posting.notifier.enumeration.Level;
import com.lvnlx.job.posting.notifier.enumeration.Method;
import com.lvnlx.job.posting.notifier.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final HttpService httpService;
    private final String notificationTopic;

    NotificationService(HttpService httpService) {
        this.httpService = httpService;
        this.notificationTopic = System.getenv("NOTIFICATION_TOPIC");
    }

    public void sendNotification(String title, String message, Level level) {
        try {
            httpService.sendRequest(Method.POST, String.format("https://ntfy.sh/%s", notificationTopic), message, "Title", title, "X-Tags", level.icon);
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to send notification", exception);
        }
    }

    public void sendNotifications(Set<Job<?>> jobs) {
        if (!jobs.isEmpty()) {
            try {
                for (Job<?> job : jobs) {
                    httpService.sendRequest(Method.POST, String.format("https://ntfy.sh/%s", notificationTopic), job.getTitle(), "Title", String.format("New %s Job", job.company), "X-Tags", "briefcase", "Actions", String.format("view, Posting, %s", job.getLink()));
                    logger.info("Sent notification for job {}", job.getId());
                }
            } catch (IOException | InterruptedException exception) {
                logger.error("Unable to send job notification", exception);
            }
        } else {
            logger.info("No new jobs");
        }
    }
}
