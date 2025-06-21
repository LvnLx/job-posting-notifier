package com.lvnlx.api.change.notifier;

import com.lvnlx.api.change.notifier.service.ApiChangeNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final AtomicReference<Set<String>> currentJobIds = new AtomicReference<>(new HashSet<>());

    public static void main(String[] args) {
        logger.info("Starting API change notifier");
        ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Set<String> updatedJobIds = ApiChangeNotifier.refreshJobs(currentJobIds.get());
            currentJobIds.set(updatedJobIds);
        }, 0, 10, SECONDS);
    }
}