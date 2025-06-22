package com.lvnlx.api.change.notifier.service;

import com.lvnlx.api.change.notifier.client.Client;
import com.lvnlx.api.change.notifier.enumeration.Method;
import com.lvnlx.api.change.notifier.model.Job;
import com.lvnlx.api.change.notifier.model.JobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApiChangeNotifier {
    private static final Logger logger = LoggerFactory.getLogger(ApiChangeNotifier.class);

    private final HttpService httpService;
    private Set<String> currentJobIds;

    ApiChangeNotifier(HttpService httpService) {
        this.httpService = httpService;
        this.currentJobIds = new HashSet<>();
    }

    @Scheduled(fixedRate = 10000)
    public void refreshJobs() {
        try {
            JobResult jobResult = getNewJobs(currentJobIds);
            sendNotifications(jobResult.newJobs);
            currentJobIds = jobResult.jobIds;
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to retrieve new jobs", exception);
            sendNotification(String.format("Unable to retrieve new jobs: %s", exception.getMessage()));
        }
    }

    private JobResult getNewJobs(Set<String> currentJobIds) throws IOException, InterruptedException {
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

    private void sendNotification(String message) {
        try {
            httpService.sendRequest(Method.POST, "https://ntfy.sh/my-super-test-topic-123", message, "Title", "API Change Notifier Down", "X-Tags", "warning");
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to send notification", exception);
        }
    }

    private void sendNotifications(Set<Job<?>> jobs) {
        if (!jobs.isEmpty()) {
            try {
                for (Job<?> job : jobs) {
                    httpService.sendRequest(Method.POST, "https://ntfy.sh/my-super-test-topic-123", job.getTitle(), "Title", String.format("New %s Job", job.company), "X-Tags", "briefcase", "Actions", String.format("view, Posting, %s", job.getLink()));
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
