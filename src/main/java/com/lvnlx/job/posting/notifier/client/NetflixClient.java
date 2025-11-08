package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.netflix.NetflixJob;
import com.lvnlx.job.posting.notifier.model.job.netflix.response.JobsResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NetflixClient extends Client<NetflixJob> {
    NetflixClient(BigQuery bigQuery, HttpService httpService, NotificationService notificationService) throws InterruptedException {
        super(
                "netflix",
                "Netflix",
                bigQuery,
                httpService,
                notificationService,
                List.of("security", "site reliability", "ui", "android", "ios"),
                List.of("4")
        );
    }

    @Override
    protected List<NetflixJob> getAllJobs() throws IOException, InterruptedException {
        return getAll(0, Integer.MAX_VALUE)
                .stream()
                .flatMap(response -> response.positions.stream())
                .map(response -> new NetflixJob(response, this))
                .toList();
    }

    private List<JobsResponse> getAll(int offset, int total) throws IOException, InterruptedException {
        if (offset > total) {
            return new ArrayList<>();
        }

        int PAGE_SIZE = 10;

        JobsResponse response = httpService.sendGetRequest(String.format("https://explore.jobs.netflix.net/api/apply/v2/jobs?start=%d&num=%d&Teams=Engineering&Work%%20Type=remote&Region=ucan&sort_by=relevance", offset, PAGE_SIZE), JobsResponse.class);
        List<JobsResponse> responses = getAll(offset + PAGE_SIZE, response.count);
        responses.add(response);

        return responses;
    }
}
