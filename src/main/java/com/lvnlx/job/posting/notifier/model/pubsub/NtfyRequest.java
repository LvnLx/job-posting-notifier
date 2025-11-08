package com.lvnlx.job.posting.notifier.model.pubsub;

import com.lvnlx.job.posting.notifier.constant.Environment;

import java.util.List;

public class NtfyRequest {
    public NtfyRequest(String title, String message, NtfyRequestAction action, List<String> tags) {
        this.actions = List.of(action);
        this.message = message;
        this.tags = tags;
        this.title = title;
        this.topic = Environment.NTFY_TOPIC;
    }

    public NtfyRequest(String title, String message, List<String> tags) {
        this.actions = List.of();
        this.message = message;
        this.tags = tags;
        this.title = title;
        this.topic = Environment.NTFY_TOPIC;
    }

    public final List<NtfyRequestAction> actions;
    public final String message;
    public final List<String> tags;
    public final String title;
    public final String topic;
}
