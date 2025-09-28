package com.akshay.CareerNest.service;

import com.akshay.CareerNest.entity.Application;
import com.akshay.CareerNest.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EmailService emailService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Apply to job
    public Application applyToJob(String jobId, Application application, MultipartFile resume) throws IOException {
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String resumeFileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
        String resumePath = uploadFolder.getAbsolutePath() + File.separator + resumeFileName;
        resume.transferTo(new File(resumePath));

        application.setJobId(jobId);
        application.setResumePath(resumePath);

        Application savedApp = applicationRepository.save(application);

        // Optional: send email
        emailService.sendJobApplicationEmail(application.getEmail(), jobId, application.getName());

        return savedApp;
    }
}
