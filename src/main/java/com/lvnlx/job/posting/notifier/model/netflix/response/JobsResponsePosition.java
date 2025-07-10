package com.lvnlx.job.posting.notifier.model.netflix.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobsResponsePosition {
    public String canonicalPositionUrl;
    public String display_job_id;
    public String name;
}
