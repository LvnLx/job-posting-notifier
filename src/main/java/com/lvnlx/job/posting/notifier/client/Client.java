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

    private boolean sentFailure;
    final String name;
    final NotificationService notificationService;
    final HttpService httpService;

    Client(String name, HttpService httpService, NotificationService notificationService) {
        this.sentFailure = false;
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
            return getAllJobs();
        } catch (IOException | InterruptedException exception) {
            logger.error("Unable to retrieve new jobs for {} client", name, exception);

            if (!sentFailure) {
                notificationService.sendNotification(String.format("Unable to retrieve new jobs for %s client", name), exception.getMessage(), Level.ERROR);
                sentFailure = true;
            }

            return new ArrayList<>();
        }
    }

    protected abstract List<T> getAllJobs() throws IOException, InterruptedException;
}
