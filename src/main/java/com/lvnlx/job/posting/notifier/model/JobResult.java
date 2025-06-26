package com.lvnlx.job.posting.notifier.model;

import java.util.Set;

public class JobResult {
    public Set<String> jobIds;
    public Set<Job<?>> newJobs;

    public JobResult(Set<String> jobIds, Set<Job<?>> newJobs) {
        this.jobIds = jobIds;
        this.newJobs = newJobs;
    }
}
