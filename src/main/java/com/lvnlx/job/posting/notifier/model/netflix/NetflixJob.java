package com.lvnlx.job.posting.notifier.model.netflix;

import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.model.netflix.response.JobsResponsePosition;

public class NetflixJob extends Job<JobsResponsePosition> {
    public NetflixJob(JobsResponsePosition job) {
        super("Netflix", job);
    }

    @Override
    public String getId() {
        return String.valueOf(this.job.id);
    }

    @Override
    public String getTitle() {
        return this.job.name;
    }

    @Override
    public String getLink() {
        return this.job.canonicalPositionUrl;
    }
}
