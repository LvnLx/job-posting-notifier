package com.lvnlx.job.posting.notifier.model.job.apple.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseRes {
    public List<SearchResponseResSearchResult> searchResults;
    public int totalRecords;
}
