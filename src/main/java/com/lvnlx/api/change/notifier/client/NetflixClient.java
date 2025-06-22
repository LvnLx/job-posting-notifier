package com.lvnlx.api.change.notifier.client;

import com.lvnlx.api.change.notifier.enumeration.Method;
import com.lvnlx.api.change.notifier.model.netflix.NetflixJob;
import com.lvnlx.api.change.notifier.model.netflix.response.JobsResponse;
import com.lvnlx.api.change.notifier.service.HttpService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NetflixClient extends Client<NetflixJob> {
    NetflixClient(HttpService httpService) {
        super(httpService);
    }

    @Override
    protected List<NetflixJob> getAllJobs() throws IOException, InterruptedException {
        return getAll(0, Integer.MAX_VALUE)
                .stream()
                .flatMap(response -> response.positions.stream())
                .filter(position -> position.name.contains("L4"))
                .map(NetflixJob::new)
                .toList();
    }

    private List<JobsResponse> getAll(int offset, int total) throws IOException, InterruptedException {
        if (offset > total) {
            return new ArrayList<>();
        }

        int PAGE_SIZE = 10;

        JobsResponse response = httpService.sendRequest(Method.GET, String.format("https://explore.jobs.netflix.net/api/apply/v2/jobs?start=%d&num=%d&Teams=Engineering&Work%%20Type=remote&Region=ucan&sort_by=relevance", offset, PAGE_SIZE), JobsResponse.class);
        List<JobsResponse> responses = getAll(offset + PAGE_SIZE, response.count);
        responses.add(response);

        return responses;
    }
}
