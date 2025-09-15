package com.akshay.CareerNest.controller;

import com.akshay.CareerNest.entity.JobPost;
import com.akshay.CareerNest.service.JobPostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruiter/private")
@Tag(name = "Recruiter API's",description = "Create Job post, Get All Job Posts, Update Job Post, Delete Job Post, Get count of Applicants")
public class RecruiterController {

    private final JobPostService jobPostService;

    public RecruiterController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    // ✅ Create Job Post
    @PostMapping("/jobs")
    public JobPost createJob(@RequestBody JobPost jobPost) {
        return jobPostService.createJob(jobPost);
    }

    // ✅ Get All Job Posts
    @GetMapping("/jobs")
    public List<JobPost> getAllJobs() {
        return jobPostService.getAllJobs();
    }

    // ✅ Update Job Post
    @PutMapping("/jobs/{id}")
    public JobPost updateJob(@PathVariable String id, @RequestBody JobPost jobDetails) {
        return jobPostService.updateJob(id, jobDetails);
    }

    // ✅ Delete Job Post
    @DeleteMapping("/jobs/{id}")
    public void deleteJob(@PathVariable String id) {
        jobPostService.deleteJob(id);
    }

    @GetMapping("/jobs/count/{id}")
    public long count(@PathVariable String id)
    {
     return jobPostService.countApplicationsForJob(id);
    }
}
