package com.akshay.CareerNest.service;

import com.akshay.CareerNest.entity.Application;
import com.akshay.CareerNest.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    private static final String UPLOAD_DIR = "uploads/resumes/";

    public Application applyToJob(String jobId, Application application, MultipartFile resume) throws IOException {
        // Ensure uploads directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate unique file name for resume
        String resumeFileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
        String resumePath = UPLOAD_DIR + resumeFileName;

        // Save resume to local file system
        resume.transferTo(new File(resumePath));

        // Set values in application
        application.setJobId(jobId);
        application.setResumePath(resumePath);

        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsForJob(String jobId) {
        return applicationRepository.findByJobId(jobId);
    }
}
