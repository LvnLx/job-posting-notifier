package com.lvnlx.job.posting.notifier.model.apple;

import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.model.apple.response.SearchResponseResSearchResult;

public class AppleJob extends Job<SearchResponseResSearchResult> {
    public AppleJob(SearchResponseResSearchResult job) {
        super("Apple", job);
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
