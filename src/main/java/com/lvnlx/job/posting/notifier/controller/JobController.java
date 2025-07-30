package com.lvnlx.job.posting.notifier.controller;

import com.lvnlx.job.posting.notifier.model.Job;
import com.lvnlx.job.posting.notifier.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class JobController {
    private final JobService service;

    JobController(JobService jobService) {
        this.service = jobService;
    }

    @GetMapping("/jobs")
    public String getJobs(Model model) {
        Map<String, List<Job<?>>> jobsByCompany = service.getJobsByCompany();
        model.addAttribute("jobsByCompany", jobsByCompany);
        return "jobs";
    }
}
