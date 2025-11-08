package com.lvnlx.job.posting.notifier.model.job.epicgames;

import com.lvnlx.job.posting.notifier.client.EpicGamesClient;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.model.job.epicgames.response.JobResponseHit;

public class EpicGamesJob extends Job<JobResponseHit, EpicGamesClient> {
    public EpicGamesJob(JobResponseHit job, EpicGamesClient client) {
        super(job, client);
    }

    @Override
    public String getId() {
        return this.job.requisition_id;
    }

    @Override
    public String getTitle() {
        return this.job.title;
    }

    @Override
    public String getLink() {
        return this.job.absolute_url;
    }
}
