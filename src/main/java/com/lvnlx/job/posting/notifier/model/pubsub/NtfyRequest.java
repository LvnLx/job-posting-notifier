package com.lvnlx.job.posting.notifier.model.pubsub;

import java.util.List;

public class NtfyRequest {
    private static final String ntfyTopic = System.getenv("NTFY_TOPIC");

    public NtfyRequest(String title, String message, NtfyRequestAction action, List<String> tags) {
        this.actions = List.of(action);
        this.message = message;
        this.tags = tags;
        this.title = title;
        this.topic = NtfyRequest.ntfyTopic;
    }

    public NtfyRequest(String title, String message, List<String> tags) {
        this.actions = List.of();
        this.message = message;
        this.tags = tags;
        this.title = title;
        this.topic = NtfyRequest.ntfyTopic;
    }

    public final List<NtfyRequestAction> actions;
    public final String message;
    public final List<String> tags;
    public final String title;
    public final String topic;
}
