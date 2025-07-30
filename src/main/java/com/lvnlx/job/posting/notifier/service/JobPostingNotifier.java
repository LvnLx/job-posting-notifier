package com.lvnlx.job.posting.notifier.service;

import com.lvnlx.job.posting.notifier.client.Client;
import com.lvnlx.job.posting.notifier.enumeration.Level;
import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.model.JobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobPostingNotifier {
    private static final Logger logger = LoggerFactory.getLogger(JobPostingNotifier.class);

    private final NotificationService notificationService;
    private Set<String> currentJobIds;

    JobPostingNotifier(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.currentJobIds = new HashSet<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendStartupNotification() {
        notificationService.sendNotification("Job posting notifications starting", "You will begin receiving new job postings shortly", Level.INFO);
    }

    @Scheduled(fixedRate = 600000)
    @CacheEvict(value = "jobs", allEntries = true)
    public void refreshJobs() {
        logger.info("Refreshing jobs");
        JobResult jobResult = getNewJobs(currentJobIds);
        notificationService.sendNotifications(jobResult.newJobs);
        currentJobIds = jobResult.jobIds;
    }

    private JobResult getNewJobs(Set<String> currentJobIds) {
        List<Job<?>> jobs = Client.getJobsFromAll();

        HashSet<String> updatedJobIds = new HashSet<>();
        HashSet<Job<?>> newJobs = new HashSet<>();
        for (Job<?> job : jobs) {
            if (!currentJobIds.contains(job.getId())) {
                newJobs.add(job);
            }

            updatedJobIds.add(job.getId());
        }

        return new JobResult(updatedJobIds, newJobs);
    }
}
