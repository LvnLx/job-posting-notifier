package com.lvnlx.job.posting.notifier.model.job.spotify;

import com.lvnlx.job.posting.notifier.client.SpotifyClient;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.model.job.spotify.response.SearchResponseJob;

public class SpotifyJob extends Job<SearchResponseJob, SpotifyClient> {
    public SpotifyJob(SearchResponseJob job, SpotifyClient client) {
        super(job, client);
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
