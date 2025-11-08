package com.lvnlx.job.posting.notifier.model.job.epicgames.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobResponse {
    public List<JobResponseHit> hits;
}
