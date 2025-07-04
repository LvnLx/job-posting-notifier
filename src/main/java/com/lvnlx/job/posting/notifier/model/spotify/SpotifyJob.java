package com.lvnlx.job.posting.notifier.model.spotify;

import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.model.spotify.response.SearchResponseJob;

public class SpotifyJob extends Job<SearchResponseJob> {
    public SpotifyJob(SearchResponseJob job) {
        super("Spotify", job);
    }

    @Override
    public String getId() {
        return this.job.id;
    }

    @Override
    public String getTitle() {
        return this.job.text;
    }

    @Override
    public String getLink() {
        return String.format("https://www.lifeatspotify.com/jobs/%s", this.job.id);
    }
}
