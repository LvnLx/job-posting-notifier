package com.lvnlx.job.posting.notifier.model.pubsub;

public class NtfyRequestAction {
    public NtfyRequestAction(String url) {
        this.action = "view";
        this.label = "Posting";
        this.url = url;
    }

    public final String action;
    public final String label;
    public final String url;
}
