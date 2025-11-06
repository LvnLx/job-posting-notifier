package com.lvnlx.job.posting.notifier.model.pubsub;

import java.util.List;

public class NtfyRequest {
    private static final String ntfyTopic = System.getenv("NTFY_TOPIC");

    public NtfyRequest(String title, String message, String click, List<String> tags) {
        this.click = click;
        this.message = message;
        this.tags = tags;
        this.title = title;
        this.topic = NtfyRequest.ntfyTopic;
    }

    public NtfyRequest(String title, String message, List<String> tags) {
        this.click = null;
        this.message = message;
        this.tags = tags;
        this.title = title;
        this.topic = NtfyRequest.ntfyTopic;
    }

    public String click;
    public String message;
    public List<String> tags;
    public String title;
    public String topic;
}
