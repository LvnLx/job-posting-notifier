package com.lvnlx.api.change.notifier.service;

import com.lvnlx.api.change.notifier.enumeration.Method;
import com.lvnlx.api.change.notifier.model.spotify.Job;
import com.lvnlx.api.change.notifier.model.JobResult;
import com.lvnlx.api.change.notifier.model.spotify.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ApiNotifier {
    private final Logger logger = LoggerFactory.getLogger(ApiNotifier.class);
    private final HttpService httpService;

    public ApiNotifier() {
        this.httpService = new HttpService();
    }

    public Set<String> refreshJobs(Set<String> currentJobIds) {
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

    private JobResult getNewJobs(Set<String> currentJobIds) throws IOException, InterruptedException {
        SearchResponse searchResponse = httpService.sendRequest(Method.GET, "https://api-dot-new-spotifyjobs-com.nw.r.appspot.com/wp-json/animal/v1/job/search?l=berlin%2Cchicago%2Clos-angeles%2Cnashville%2Cnew-york%2Ctoronto&c=backend%2Cclient-c%2Cdeveloper-tools-infrastructure%2Cnetwork-engineering-it%2Csecurity%2Cweb", SearchResponse.class);

        HashSet<String> updatedJobIds = new HashSet<>();
        HashSet<Job> newJobs = new HashSet<>();
        for (Job job : searchResponse.result) {
            if (!currentJobIds.contains(job.id)) {
                newJobs.add(job);
            }

            updatedJobIds.add(job.id);
        }

        return new JobResult(updatedJobIds, newJobs);
    }

    private void sendNotification(String message) {
        try {
            httpService.sendRequest(Method.POST, "https://ntfy.sh/my-super-test-topic-123", message, "Title", "API Notifier Down", "X-Tags", "warning");
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to send notification", exception);
        }
    }

    private void sendNotifications(Set<Job> jobs) {
        if (!jobs.isEmpty()) {
            try {
                for (Job job : jobs) {
                    httpService.sendRequest(Method.POST, "https://ntfy.sh/my-super-test-topic-123", job.text, "Title", "New Job", "X-Tags", "briefcase", "Actions", String.format("view, Posting, https://www.lifeatspotify.com/jobs/%s", job.id));
                    logger.info("Sent notification for job {}", job.id);
                }
            } catch (IOException | InterruptedException exception) {
                logger.error("Unable to send job notification", exception);
            }
        } else {
            logger.info("No new jobs");
        }
    }
}
