package com.lvnlx.api.change.notifier.client;

import com.lvnlx.api.change.notifier.enumeration.Method;
import com.lvnlx.api.change.notifier.model.spotify.SpotifyJob;
import com.lvnlx.api.change.notifier.model.spotify.response.SearchResponse;
import com.lvnlx.api.change.notifier.service.HttpService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpotifyClient extends Client<SpotifyJob> {
    public SpotifyClient(HttpService httpService) {
        super(httpService);
    }

    @Override
    protected List<SpotifyJob> getAllJobs() throws IOException, InterruptedException {
        SearchResponse response = httpService.sendRequest(Method.GET, "https://api-dot-new-spotifyjobs-com.nw.r.appspot.com/wp-json/animal/v1/job/search?l=berlin%2Cchicago%2Clos-angeles%2Cnashville%2Cnew-york%2Ctoronto&c=backend%2Cclient-c%2Cdeveloper-tools-infrastructure%2Cnetwork-engineering-it%2Csecurity%2Cweb", SearchResponse.class);
        return response.result.stream().map(SpotifyJob::new).toList();

    }
}
