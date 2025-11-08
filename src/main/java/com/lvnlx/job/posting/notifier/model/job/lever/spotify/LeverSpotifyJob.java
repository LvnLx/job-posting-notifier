package com.lvnlx.job.posting.notifier.model.job.lever.spotify;

import com.lvnlx.job.posting.notifier.client.LeverSpotifyClient;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.model.job.lever.spotify.response.Posting;

public class LeverSpotifyJob extends Job<Posting, LeverSpotifyClient> {
    public LeverSpotifyJob(Posting job, LeverSpotifyClient client) {
        super(job, client);
    }

    @Override
    public String getId() {
        return job.id;
    }

    @Override
    public String getTitle() {
        return job.text;
    }

    @Override
    public String getLink() {
        return job.hostedUrl;
    }
}
