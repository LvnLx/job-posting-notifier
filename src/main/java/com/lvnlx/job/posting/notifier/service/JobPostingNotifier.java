package com.lvnlx.job.posting.notifier.service;

import com.lvnlx.job.posting.notifier.client.Client;
import com.lvnlx.job.posting.notifier.enumeration.Level;
import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostingNotifier {
    private static final Logger logger = LoggerFactory.getLogger(JobPostingNotifier.class);

    private final BigQuery bigQuery;
    private final NotificationService notificationService;

    JobPostingNotifier(BigQuery bigQuery, NotificationService notificationService) {
        this.bigQuery = bigQuery;
        this.notificationService = notificationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendStartupNotification() {
        notificationService.sendNotification("Job posting notifications active", "You will begin receiving new job postings shortly", Level.INFO);
    }

    @Scheduled(fixedRate = 600000)
    @CacheEvict(value = "jobs", allEntries = true)
    public void refreshJobs() throws InterruptedException {
        logger.info("Refreshing jobs");
        List<Job<?, ?>> newJobs = getNewJobs();
        notificationService.sendNotifications(newJobs);
        bigQuery.createJobPostings(newJobs);
    }

    private List<Job<?, ?>> getNewJobs() throws InterruptedException {
        List<String> currentJobIds;

        try {
            currentJobIds = bigQuery.getPostingIds().toList();
        } catch (InterruptedException exception) {
            logger.error("Failed to get posting IDs", exception);
            throw exception;
        }

        List<Job<?, ?>> jobs = Client.getJobsFromAll();

        ArrayList<Job<?, ?>> newJobs = new ArrayList<>();
        for (Job<?, ?> job : jobs) {
            if (!currentJobIds.contains(job.getId())) {
                newJobs.add(job);
            }
        }

        return newJobs;
    }
}
