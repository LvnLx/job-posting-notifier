package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.model.lever.spotify.LeverSpotifyJob;
import com.lvnlx.job.posting.notifier.model.lever.spotify.response.Posting;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class LeverSpotifyClient extends Client<LeverSpotifyJob> {
    LeverSpotifyClient(HttpService httpService, NotificationService notificationService) {
        super("Lever", httpService, notificationService);
    }

    @Override
    protected List<LeverSpotifyJob> getAllJobs() throws IOException, InterruptedException {
        return Arrays.stream(httpService.sendGetRequest("https://api.lever.co/v0/postings/spotify?mode=json&department=Engineering", Posting[].class))
                .filter(posting -> !posting.text.contains("Machine Learning") && !posting.text.contains("Manager") && !posting.text.contains("Data Engineer") && posting.country.equals("US"))
                .map(LeverSpotifyJob::new)
                .toList();
    }
}
