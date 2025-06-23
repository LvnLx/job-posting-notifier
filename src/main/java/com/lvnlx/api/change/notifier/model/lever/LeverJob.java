package com.lvnlx.api.change.notifier.model.lever;

import com.lvnlx.api.change.notifier.model.Job;
import com.lvnlx.api.change.notifier.model.lever.response.Posting;

public class LeverJob extends Job<Posting> {
    public LeverJob(Posting job) {
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
