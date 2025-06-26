package com.lvnlx.job.posting.notifier.model.lever.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Posting {
    public String country;
    public String id;
    public String hostedUrl;
    public String text;
}
