package com.lvnlx.api.change.notifier.model.spotify.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseJob {
    public String id;
    public String text;
}
