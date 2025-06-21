package com.lvnlx.api.change.notifier.model.netflix;

import com.lvnlx.api.change.notifier.model.Job;
import com.lvnlx.api.change.notifier.model.netflix.response.JobsResponse;
import com.lvnlx.api.change.notifier.model.netflix.response.JobsResponsePosition;

public class NetflixJob extends Job<JobsResponse, JobsResponsePosition> {
    public NetflixJob(Class<JobsResponse> responseTemplate, JobsResponsePosition job) {
        super(responseTemplate, job);
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
