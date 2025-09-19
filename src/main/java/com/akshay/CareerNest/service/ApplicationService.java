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

    @Autowired
    private EmailService emailService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Application applyToJob(String jobId, Application application, MultipartFile resume) throws IOException {
        // Ensure uploads directory exists
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Generate unique file name
        String resumeFileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
        String resumePath = uploadFolder.getAbsolutePath() + File.separator + resumeFileName;

        // Save resume
        resume.transferTo(new File(resumePath));

        // Set values in application
        application.setJobId(jobId);
        application.setResumePath(resumePath);

        // Save application in DB
        Application savedApplication = applicationRepository.save(application);

        //  Send confirmation email
        emailService.sendJobApplicationEmail(
                application.getEmail(),      // Assuming Application entity has email
                jobId,
                application.getName()        // Assuming Application entity has name
        );

        return savedApplication;
    }
}
