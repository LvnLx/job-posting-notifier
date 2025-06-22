package com.lvnlx.api.change.notifier.model;

public abstract class Job<T> {
    protected final T job;
    public final String company;

    protected Job(String company, T job) {
        this.company = company;
        this.job = job;
    }

    public abstract String getId();

    public abstract String getTitle();

    public abstract String getLink();
}
