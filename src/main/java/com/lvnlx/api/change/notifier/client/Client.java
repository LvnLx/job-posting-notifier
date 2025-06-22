package com.lvnlx.api.change.notifier.client;

import com.lvnlx.api.change.notifier.model.Job;
import com.lvnlx.api.change.notifier.service.HttpService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Client<T extends Job<?>> {
    private static final List<Client<?>> clients = new ArrayList<>();

    final HttpService httpService;

    Client(HttpService httpService) {
        this.httpService = httpService;
        Client.clients.add(this);
    }

    public static List<Job<?>> getJobsFromAll() throws IOException, InterruptedException {
        List<Job<?>> jobs = new ArrayList<>();

        for (Client<?> client : clients) {
            jobs.addAll(client.getAllJobs());
        }

        return jobs;
    }

    protected abstract List<T> getAllJobs() throws IOException, InterruptedException;
}
