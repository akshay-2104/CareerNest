package com.akshay.CareerNest.controller;

import com.akshay.CareerNest.entity.Application;
import com.akshay.CareerNest.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Candidate applies for a specific job
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<Application> applyForJob(
            @PathVariable String jobId,
            @RequestParam("application") String applicationJson,
            @RequestParam("resume") MultipartFile resume) throws IOException {

        // Convert JSON string into Application object
        ObjectMapper objectMapper = new ObjectMapper();
        Application application = objectMapper.readValue(applicationJson, Application.class);

        Application savedApplication = applicationService.applyToJob(jobId, application, resume);
        return ResponseEntity.ok(savedApplication);
    }

    // Recruiter views all applications for a specific job
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsForJob(@PathVariable String jobId) {
        List<Application> applications = applicationService.getApplicationsForJob(jobId);
        return ResponseEntity.ok(applications);
    }
}
