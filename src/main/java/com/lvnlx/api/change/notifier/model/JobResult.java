package com.lvnlx.api.change.notifier.model;

import com.lvnlx.api.change.notifier.model.spotify.Job;

import java.util.Set;

public class JobResult {
    public Set<String> jobIds;
    public Set<Job> newJobs;

    public JobResult(Set<String> jobIds, Set<Job> newJobs) {
        this.jobIds = jobIds;
        this.newJobs = newJobs;
    }
}
