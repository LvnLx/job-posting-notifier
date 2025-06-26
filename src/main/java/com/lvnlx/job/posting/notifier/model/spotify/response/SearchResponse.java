package com.lvnlx.job.posting.notifier.model.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    public List<SearchResponseJob> result;
}
