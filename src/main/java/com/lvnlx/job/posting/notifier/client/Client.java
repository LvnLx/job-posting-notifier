package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.enumeration.Level;
import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.Job;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Client<T extends Job<?, ?>> {
    private static final List<Client<?>> clients = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private final BigQuery bigQuery;
    private final String id;
    private final String name;
    private final NotificationService notificationService;
    private final List<String> titleExclusions;
    private final List<String> titleInclusions;

    private int failureCount;

    protected final HttpService httpService;

    Client(String id, String name, BigQuery bigQuery, HttpService httpService, NotificationService notificationService, List<String> titleExclusions, List<String> titleInclusions) throws InterruptedException, RuntimeException {
        this.failureCount = 0;

        this.id = id;
        this.name = name;
        this.bigQuery = bigQuery;
        this.httpService = httpService;
        this.notificationService = notificationService;
        this.titleExclusions = titleExclusions.stream().map(String::toLowerCase).toList();
        this.titleInclusions = titleInclusions.stream().map(String::toLowerCase).toList();

        Client.clients.add(this);
        optionallyCreateCompany();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Job<?, ?>> getJobsFromAll() {
        List<Job<?, ?>> jobs = new ArrayList<>();

        for (Client<?> client : clients) {
            List<? extends Job<?, ?>> clientFilteredJobs = client.getAllJobsSafely().stream().filter(job -> client.titleExclusions.stream().noneMatch(exclusion -> job.getTitle().toLowerCase().contains(exclusion))).filter(job -> client.titleInclusions.stream().anyMatch(inclusion -> job.getTitle().toLowerCase().contains(inclusion))).toList();

            jobs.addAll(clientFilteredJobs);
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

    private void optionallyCreateCompany() throws InterruptedException, RuntimeException {
        boolean doesCompanyExist;

        try {
            doesCompanyExist = bigQuery.doesCompanyExist(this.getId());
        } catch (InterruptedException exception) {
            logger.error("Failed to check if company exists", exception);
            throw exception;
        }

        if (!doesCompanyExist) {
            bigQuery.createCompany(this);
        }
    }

    protected abstract List<T> getAllJobs() throws IOException, InterruptedException;
}
