package com.lvnlx.job.posting.notifier.model.job.netflix;

import com.lvnlx.job.posting.notifier.client.NetflixClient;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.model.job.netflix.response.JobsResponsePosition;

public class NetflixJob extends Job<JobsResponsePosition, NetflixClient> {
    public NetflixJob(JobsResponsePosition job, NetflixClient client) {
        super(job, client);
    }

    @Override
    public String getId() {
        return String.valueOf(this.job.display_job_id);
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
