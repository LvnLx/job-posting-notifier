package com.lvnlx.job.posting.notifier.client;

import com.lvnlx.job.posting.notifier.enumeration.Method;
import com.lvnlx.job.posting.notifier.model.lever.LeverJob;
import com.lvnlx.job.posting.notifier.model.lever.response.Posting;
import com.lvnlx.job.posting.notifier.service.HttpService;
import com.lvnlx.job.posting.notifier.service.NotificationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class LeverClient extends Client<LeverJob> {
    LeverClient(HttpService httpService, NotificationService notificationService) {
        super("Lever", httpService, notificationService);
    }

    @Override
    protected List<LeverJob> getAllJobs() throws IOException, InterruptedException {
        return Arrays.stream(httpService.sendRequest(Method.GET, "https://api.lever.co/v0/postings/spotify?mode=json&department=Engineering", Posting[].class))
                .filter(posting -> posting.text.contains("Backend Engineer") && posting.country.equals("US"))
                .map(LeverJob::new)
                .toList();
    }
}
