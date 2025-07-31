package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.enumeration.Method;
import com.lvnlx.job.posting.notifier.model.epicgames.EpicGamesJob;
import com.lvnlx.job.posting.notifier.model.epicgames.response.JobResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EpicGamesClient extends Client<EpicGamesJob> {
    EpicGamesClient(HttpService httpService, NotificationService notificationService) {
        super("Epic Games", httpService, notificationService);
    }

    @Override
    protected List<EpicGamesJob> getAllJobs() throws IOException, InterruptedException {
        return getAll(0, Integer.MAX_VALUE)
                .stream()
                .flatMap(response -> response.hits.stream())
                .filter(hit -> !hit.title.contains("EAC") && !hit.title.contains("Mobile") && !hit.title.contains("Data") && !hit.title.contains("Animation") && !hit.title.contains("Director") && !hit.title.contains("Lead") && !hit.title.contains("Principal") && !hit.title.contains("Senior"))
                .map(EpicGamesJob::new)
                .toList();
    }

    private List<JobResponse> getAll(int offset, int total) throws IOException, InterruptedException {
        if (offset > total) {
            return new ArrayList<>();
        }

        int PAGE_SIZE = 10;

        JobResponse response = httpService.sendRequest(Method.GET, String.format("https://greenhouse-service.debc.live.use1a.on.epicgames.com/api/job?limit=%d&skip=%d&department=Engineering&country=United%%20States", PAGE_SIZE, offset), JobResponse.class);
        List<JobResponse> responses = getAll(offset + PAGE_SIZE, offset + response.hits.size());
        responses.add(response);

        return responses;
    }
}
