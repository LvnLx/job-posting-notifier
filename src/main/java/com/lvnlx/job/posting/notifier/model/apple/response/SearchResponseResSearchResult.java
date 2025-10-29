package com.lvnlx.job.posting.notifier.model.apple.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseResSearchResult {
    public String id;
    public String postingTitle;
    public String transformedPostingTitle;
    public SearchResponseResSearchResultTeam team;
}
