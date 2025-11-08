package com.lvnlx.job.posting.notifier.model.job.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseJob {
    public String id;
    public String text;
}
