package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.lever.spotify.LeverSpotifyJob;
import com.lvnlx.job.posting.notifier.model.job.lever.spotify.response.Posting;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class LeverSpotifyClient extends Client<LeverSpotifyJob> {
    LeverSpotifyClient(BigQuery bigQuery, HttpService httpService, NotificationService notificationService) throws InterruptedException {
        super(
                "lever-spotify",
                "Lever (Spotify)",
                bigQuery,
                httpService,
                notificationService,
                SpotifyClient.exclusions,
                SpotifyClient.inclusions
        );
    }

    @Override
    protected List<LeverSpotifyJob> getAllJobs() throws IOException, InterruptedException {
        return Arrays
                .stream(httpService.sendGetRequest("https://api.lever.co/v0/postings/spotify?mode=json&department=Engineering", Posting[].class))
                .filter(posting -> posting.country.equals("US"))
                .map(posting -> new LeverSpotifyJob(posting, this))
                .toList();
    }
}
