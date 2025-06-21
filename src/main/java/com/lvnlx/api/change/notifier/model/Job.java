package com.lvnlx.api.change.notifier.model;

import com.lvnlx.api.change.notifier.enumeration.Method;
import com.lvnlx.api.change.notifier.model.netflix.NetflixJob;
import com.lvnlx.api.change.notifier.model.netflix.response.JobsResponse;
import com.lvnlx.api.change.notifier.model.spotify.SpotifyJob;
import com.lvnlx.api.change.notifier.model.spotify.response.SearchResponse;
import com.lvnlx.api.change.notifier.service.HttpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Job<U, V> {
    protected final Class<U> responseTemplate;
    protected final V job;

    public static List<Job<?, ?>> getAll() throws IOException, InterruptedException {
        List<Job<?, ?>> jobs = new ArrayList<>();

        jobs.addAll(getAllNetflix());
        jobs.addAll(getAllSpotify());

        return jobs;
    }

    private static List<NetflixJob> getAllNetflix() throws IOException, InterruptedException {
        return getAll(0, Integer.MAX_VALUE)
                .stream()
                .flatMap(response -> response.positions.stream())
                .filter(position -> position.name.contains("L4"))
                .map(relevantPosition -> new NetflixJob(JobsResponse.class, relevantPosition))
                .toList();
    }

    private static List<JobsResponse> getAll(int offset, int total) throws IOException, InterruptedException {
        if (offset > total) {
            return new ArrayList<>();
        }

        int PAGE_SIZE = 10;

        JobsResponse response = HttpService.sendRequest(Method.GET, String.format("https://explore.jobs.netflix.net/api/apply/v2/jobs?start=%d&num=%d&Teams=Engineering&Work%%20Type=remote&Region=ucan&sort_by=relevance", offset, PAGE_SIZE), JobsResponse.class);
        List<JobsResponse> responses = getAll(offset + PAGE_SIZE, response.count);
        responses.add(response);

        return responses;
    }

    private static List<SpotifyJob> getAllSpotify() throws IOException, InterruptedException {
        SearchResponse response = HttpService.sendRequest(Method.GET, "https://api-dot-new-spotifyjobs-com.nw.r.appspot.com/wp-json/animal/v1/job/search?l=berlin%2Cchicago%2Clos-angeles%2Cnashville%2Cnew-york%2Ctoronto&c=backend%2Cclient-c%2Cdeveloper-tools-infrastructure%2Cnetwork-engineering-it%2Csecurity%2Cweb", SearchResponse.class);
        return response.result.stream().map(job -> new SpotifyJob(SearchResponse.class, job)).toList();
    }

    protected Job(Class<U> responseTemplate, V job) {
        this.responseTemplate = responseTemplate;
        this.job = job;
    }

    public abstract String getId();

    public abstract String getTitle();

    public abstract String getLink();
}
