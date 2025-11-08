package com.lvnlx.job.posting.notifier.model.job.epicgames.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobResponseHit {
    public String absolute_url;
    public String requisition_id;
    public String title;
}
