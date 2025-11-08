package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.gcp.BigQuery;
import com.lvnlx.job.posting.notifier.model.job.apple.AppleJob;
import com.lvnlx.job.posting.notifier.model.job.apple.request.SearchRequest;
import com.lvnlx.job.posting.notifier.model.job.apple.response.SearchResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppleClient extends Client<AppleJob> {
    AppleClient(BigQuery bigQuery, HttpService httpService, NotificationService notificationService) throws InterruptedException {
        super(
                "apple",
                "Apple",
                bigQuery,
                httpService,
                notificationService,
                List.of("research", "testing", "hardware", "site reliability"),
                List.of("engineer")
        );
    }

    @Override
    protected List<AppleJob> getAllJobs() throws IOException, InterruptedException {
        return getAll(1)
                .stream()
                .flatMap(searchResponse -> searchResponse.res.searchResults.stream())
                .map(searchResponse -> new AppleJob(searchResponse, this))
                .toList();
    }

    private List<SearchResponse> getAll(int page) throws IOException, InterruptedException {
        SearchRequest requestBody = new SearchRequest(page);
        SearchResponse response = httpService.sendPostRequest("https://jobs.apple.com/api/v1/search", requestBody, SearchResponse.class);

        if (response.res.totalRecords == 0) {
            return new ArrayList<>();
        }

        List<SearchResponse> responses = getAll(page + 1);
        responses.add(response);

        return responses;
    }
}
