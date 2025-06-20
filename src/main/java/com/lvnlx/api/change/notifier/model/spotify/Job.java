package com.lvnlx.api.change.notifier.model.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    public String id;
    public String text;
}
