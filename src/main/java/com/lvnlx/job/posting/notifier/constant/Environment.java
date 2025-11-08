package com.lvnlx.job.posting.notifier.constant;

public class Environment {
    public static final String DATASET_NAME = System.getenv("DATASET_NAME");
    public static final String NTFY_TOPIC = System.getenv("NTFY_TOPIC");
    public static final String PROJECT_ID = System.getenv("PROJECT_ID");
    public static final String PUBSUB_TOPIC = System.getenv("PUBSUB_TOPIC");
}
