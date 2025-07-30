package com.lvnlx.job.posting.notifier.service;

import com.lvnlx.job.posting.notifier.client.Client;
import com.lvnlx.job.posting.notifier.model.Job;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Cacheable("jobs")
    public Map<String, List<Job<?>>> getJobsByCompany() {
        return Client.getJobsFromAll().stream().collect(Collectors.groupingBy(job -> job.company));
    }
}
