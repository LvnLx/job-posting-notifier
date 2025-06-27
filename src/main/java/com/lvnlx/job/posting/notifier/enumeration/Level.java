package com.lvnlx.job.posting.notifier.enumeration;

public enum Level {
    INFO("information_source"),
    ERROR("x");

    public final String icon;

    Level(String icon) {
        this.icon = icon;
    }
}
