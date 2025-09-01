package com.akshay.CareerNest.service;

import com.akshay.CareerNest.entity.Application;
import com.akshay.CareerNest.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // Load path from application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    public Application applyToJob(String jobId, Application application, MultipartFile resume) throws IOException {
        // Ensure uploads directory exists
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Generate unique file name for resume
        String resumeFileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
        String resumePath = uploadFolder.getAbsolutePath() + File.separator + resumeFileName;

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
    public long countApplicationsForJob(String jobId) {
        return applicationRepository.countByJobId(jobId);
    }

}
