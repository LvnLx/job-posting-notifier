package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.model.apple.AppleJob;
import com.lvnlx.job.posting.notifier.model.apple.request.SearchRequest;
import com.lvnlx.job.posting.notifier.model.apple.response.SearchResponse;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppleClient extends Client<AppleJob> {
    AppleClient(HttpService httpService, NotificationService notificationService) {
        super("Apple", httpService, notificationService);
    }

    @Override
    protected List<AppleJob> getAllJobs() throws IOException, InterruptedException {
        return getAll(1)
                .stream()
                .flatMap(searchResponse -> searchResponse.res.searchResults.stream())
                .filter(searchResult -> !searchResult.postingTitle.toLowerCase().contains("research") && !searchResult.postingTitle.toLowerCase().contains("testing") && searchResult.postingTitle.toLowerCase().contains("engineer")).map(AppleJob::new)
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
