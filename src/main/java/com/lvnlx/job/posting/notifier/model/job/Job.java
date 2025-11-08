package com.lvnlx.job.posting.notifier.model.job;

import com.lvnlx.job.posting.notifier.client.Client;

public abstract class Job<T, U extends Client<?>> {
    protected final T job;
    private final U client;

    protected Job(T job, U client) {
        this.client = client;
        this.job = job;
    }

    public String getCompanyId() {
        return client.getId();
    }

    public String getCompanyName() {
        return client.getName();
    }

    public abstract String getId();

    public abstract String getTitle();

    public abstract String getLink();
}
