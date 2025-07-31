package com.lvnlx.job.posting.notifier.model.epicgames;

import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.model.epicgames.response.JobResponseHit;

public class EpicGamesJob extends Job<JobResponseHit> {
    public EpicGamesJob(JobResponseHit job) {
        super("Epic Games", job);
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
