package com.lvnlx.job.posting.notifier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvnlx.job.posting.notifier.enumeration.Level;
import com.lvnlx.job.posting.notifier.gcp.Pubsub;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.model.pubsub.NtfyRequest;
import com.lvnlx.job.posting.notifier.model.pubsub.NtfyRequestAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final ObjectMapper objectMapper;
    private final Pubsub pubsub;

    NotificationService(Pubsub pubsub) {
        this.objectMapper = new ObjectMapper();
        this.pubsub = pubsub;
    }

    public void sendNotification(String title, String message, Level level) {
        try {
            NtfyRequest request = new NtfyRequest(title, message, List.of(level.icon));
            String requestString = objectMapper.writeValueAsString(request);
            pubsub.publish(requestString);
        } catch (JsonProcessingException exception) {
            logger.error("Unable to parse ntfy request", exception);
        }
    }

    public void sendNotifications(List<Job<?, ?>> jobs) {
        if (!jobs.isEmpty()) {
            try {
                for (Job<?, ?> job : jobs) {
                    NtfyRequest request = new NtfyRequest(String.format("New %s Job", job.getCompanyName()), job.getTitle(), new NtfyRequestAction(job.getLink()), List.of("briefcase"));
                    String requestString = objectMapper.writeValueAsString(request);
                    pubsub.publish(requestString);
                }
            } catch (JsonProcessingException exception) {
                logger.error("Unable to parse ntfy request", exception);
            }
        } else {
            logger.info("No new jobs");
        }
    }
}
