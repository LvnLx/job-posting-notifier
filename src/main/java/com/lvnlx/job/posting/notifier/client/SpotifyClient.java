package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.model.spotify.SpotifyJob;
import com.lvnlx.job.posting.notifier.model.spotify.response.SearchResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyClient extends Client<SpotifyJob> {
    SpotifyClient(HttpService httpService, NotificationService notificationService) {
        super(
                "Spotify",
                httpService,
                notificationService,
                new ArrayList<>(List.of("manager")),
                new ArrayList<>(List.of(""))
        );
    }

    @Override
    protected List<SpotifyJob> getAllJobs() throws IOException, InterruptedException {
        return httpService
                .sendGetRequest("https://api-dot-new-spotifyjobs-com.nw.r.appspot.com/wp-json/animal/v1/job/search?l=berlin%2Cchicago%2Clos-angeles%2Cnashville%2Cnew-york%2Ctoronto&c=backend%2Cclient-c%2Cdeveloper-tools-infrastructure%2Cnetwork-engineering-it%2Csecurity%2Cweb", SearchResponse.class)
                .result
                .stream()
                .map(SpotifyJob::new)
                .toList();
    }
}
