package com.lvnlx.job.posting.notifier.model.job.apple;

import com.lvnlx.job.posting.notifier.client.AppleClient;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.model.job.apple.response.SearchResponseResSearchResult;

public class AppleJob extends Job<SearchResponseResSearchResult, AppleClient> {
    public AppleJob(SearchResponseResSearchResult job, AppleClient client) {
        super(job, client);
    }

    @Override
    public String getId() {
        return this.job.id;
    }

    @Override
    public String getTitle() {
        return this.job.postingTitle;
    }

    @Override
    public String getLink() {
        return String.format("https://jobs.apple.com/en-us/details/%s/%s?team=%s", this.job.id, this.job.transformedPostingTitle, this.job.team.teamCode);
    }
}
