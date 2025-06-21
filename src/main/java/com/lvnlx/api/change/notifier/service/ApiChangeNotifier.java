package com.lvnlx.api.change.notifier.service;

import com.lvnlx.api.change.notifier.enumeration.Method;
import com.lvnlx.api.change.notifier.model.Job;
import com.lvnlx.api.change.notifier.model.JobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApiChangeNotifier {
    private static final Logger logger = LoggerFactory.getLogger(ApiChangeNotifier.class);

    public static Set<String> refreshJobs(Set<String> currentJobIds) {
        try {
            JobResult jobResult = getNewJobs(currentJobIds);
            sendNotifications(jobResult.newJobs);
            return jobResult.jobIds;
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to retrieve new jobs", exception);
            sendNotification(String.format("Unable to retrieve new jobs: %s", exception.getMessage()));
            return new HashSet<>();
        }
    }

    private static JobResult getNewJobs(Set<String> currentJobIds) throws IOException, InterruptedException {
        List<Job<?, ?>> jobs = Job.getAll();

        HashSet<String> updatedJobIds = new HashSet<>();
        HashSet<Job<?, ?>> newJobs = new HashSet<>();
        for (Job<?, ?> job : jobs) {
            if (!currentJobIds.contains(job.getId())) {
                newJobs.add(job);
            }

            updatedJobIds.add(job.getId());
        }

        return new JobResult(updatedJobIds, newJobs);
    }

    private static void sendNotification(String message) {
        try {
            HttpService.sendRequest(Method.POST, "https://ntfy.sh/my-super-test-topic-123", message, "Title", "API Change Notifier Down", "X-Tags", "warning");
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to send notification", exception);
        }
    }

    private static void sendNotifications(Set<Job<?, ?>> jobs) {
        if (!jobs.isEmpty()) {
            try {
                for (Job<?, ?> job : jobs) {
                    HttpService.sendRequest(Method.POST, "https://ntfy.sh/my-super-test-topic-123", job.getTitle(), "Title", "New Job", "X-Tags", "briefcase", "Actions", String.format("view, Posting, %s", job.getLink()));
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
