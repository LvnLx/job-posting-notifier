package com.lvnlx.api.change.notifier.model.lever.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Posting {
    public String country;
    public String id;
    public String hostedUrl;
    public String text;
}
