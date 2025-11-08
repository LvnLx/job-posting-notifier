package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.epicgames.EpicGamesJob;
import com.lvnlx.job.posting.notifier.model.job.epicgames.response.JobResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EpicGamesClient extends Client<EpicGamesJob> {
    EpicGamesClient(BigQuery bigQuery, HttpService httpService, NotificationService notificationService) throws InterruptedException {
        super(
                "epic-games",
                "Epic Games",
                bigQuery,
                httpService,
                notificationService,
                List.of("intern", "senior", "eac", "lead", "principal", "director"),
                List.of("")
        );
    }

    @Override
    protected List<EpicGamesJob> getAllJobs() throws IOException, InterruptedException {
        return getAll(0, Integer.MAX_VALUE)
                .stream()
                .flatMap(response -> response.hits.stream())
                .map(response -> new EpicGamesJob(response, this))
                .toList();
    }

    private List<JobResponse> getAll(int offset, int total) throws IOException, InterruptedException {
        if (offset > total) {
            return new ArrayList<>();
        }

        int PAGE_SIZE = 10;

        JobResponse response = httpService.sendGetRequest(String.format("https://greenhouse-service.debc.live.use1a.on.epicgames.com/api/job?limit=%d&skip=%d&department=Engineering&country=United%%20States", PAGE_SIZE, offset), JobResponse.class);
        List<JobResponse> responses = getAll(offset + PAGE_SIZE, offset + response.hits.size());
        responses.add(response);

        return responses;
    }
}
