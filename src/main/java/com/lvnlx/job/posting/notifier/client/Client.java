package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.enumeration.Level;
import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Client<T extends Job<?>> {
    private static final List<Client<?>> clients = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private int failureCount;
    final String name;
    final NotificationService notificationService;
    final HttpService httpService;

    Client(String name, HttpService httpService, NotificationService notificationService) {
        this.failureCount = 0;
        this.name = name;
        this.httpService = httpService;
        this.notificationService = notificationService;
        Client.clients.add(this);
    }

    public static List<Job<?>> getJobsFromAll() {
        List<Job<?>> jobs = new ArrayList<>();

        for (Client<?> client : clients) {
            jobs.addAll(client.getAllJobsSafely());
        }

        return jobs;
    }

    private List<T> getAllJobsSafely() {
        try {
            List<T> jobs = getAllJobs();

            if (failureCount > 0) {
                notificationService.sendNotification(String.format("%s client recovered", name), String.format("Recovered after %d failures", failureCount), Level.INFO);
                failureCount = 0;
            }

            return jobs;
        } catch (IOException | InterruptedException exception) {
            logger.error(String.format("Unable to retrieve new jobs for %s client", name), exception);

            if (failureCount > 1) {
                notificationService.sendNotification(String.format("Unable to retrieve new %s jobs", name), exception.getMessage(), Level.ERROR);
            }

            return new ArrayList<>();
        }
    }

    protected abstract List<T> getAllJobs() throws IOException, InterruptedException;
}
