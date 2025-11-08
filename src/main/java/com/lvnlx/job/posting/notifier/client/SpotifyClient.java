package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.spotify.SpotifyJob;
import com.lvnlx.job.posting.notifier.model.job.spotify.response.SearchResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpotifyClient extends Client<SpotifyJob> {
    static final List<String> exclusions = List.of("manager", "senior machine", "staff machine", "research", "senior data", "senior product");
    static final List<String> inclusions = List.of("");

    SpotifyClient(BigQuery bigQuery, HttpService httpService, NotificationService notificationService) throws InterruptedException {
        super(
                "spotify",
                "Spotify",
                bigQuery,
                httpService,
                notificationService,
                exclusions,
                inclusions
        );
    }

    @Override
    protected List<SpotifyJob> getAllJobs() throws IOException, InterruptedException {
        return httpService
                .sendGetRequest("https://api-dot-new-spotifyjobs-com.nw.r.appspot.com/wp-json/animal/v1/job/search?l=boston%2Clos-angeles%2Cnashville%2Cnew-york%2Cunited-states-of-america-home-mix%2Cwashington-d-c&c=backend%2Cclient-c%2Cdata%2Cdeveloper-tools-infrastructure%2Cmobile%2Cnetwork-engineering-it%2Csecurity%2Cweb%2Cmachine-learning%2Cproduct", SearchResponse.class)
                .result
                .stream()
                .map(searchResponseJob -> new SpotifyJob(searchResponseJob, this))
                .toList();
    }
}
