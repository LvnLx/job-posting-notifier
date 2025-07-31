package com.lvnlx.job.posting.notifier.model.lever.spotify;

import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.model.lever.spotify.response.Posting;

public class LeverSpotifyJob extends Job<Posting> {
    public LeverSpotifyJob(Posting job) {
        super("Lever (Spotify)", job);
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
