package com.lvnlx.api.change.notifier;

import com.lvnlx.api.change.notifier.service.ApiNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        ApiNotifier apiNotifier = new ApiNotifier();
        AtomicReference<Set<String>> currentJobIds = new AtomicReference<>(new HashSet<>());

        logger.info("Starting notifier");

        ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Set<String> updatedJobIds = apiNotifier.refreshJobs(currentJobIds.get());
            currentJobIds.set(updatedJobIds);
        }, 0, 5, SECONDS);
    }
}