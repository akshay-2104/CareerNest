package com.akshay.CareerNest.service;

import com.akshay.CareerNest.model.JobPost;
import com.akshay.CareerNest.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public JobPost createJob(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public List<JobPost> getAllJobs() {
        return jobPostRepository.findAll();
    }

    public JobPost updateJob(Long id, JobPost jobDetails) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + id));

        jobPost.setCompanyName(jobDetails.getCompanyName());
        jobPost.setRole(jobDetails.getRole());
        jobPost.setJoiningDate(jobDetails.getJoiningDate());
        jobPost.setWorkType(jobDetails.getWorkType());
        jobPost.setPackageLpa(jobDetails.getPackageLpa());
        jobPost.setDescription(jobDetails.getDescription());
        jobPost.setDriveLink(jobDetails.getDriveLink());

        return jobPostRepository.save(jobPost);
    }

    public void deleteJob(Long id) {
        jobPostRepository.deleteById(id);
    }
}
