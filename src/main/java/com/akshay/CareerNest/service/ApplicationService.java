package com.akshay.CareerNest.service;

import com.akshay.CareerNest.entity.Application;
import com.akshay.CareerNest.entity.JobPost;
import com.akshay.CareerNest.repository.ApplicationRepository;
import com.akshay.CareerNest.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmailService emailService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Apply to job
    public Application applyToJob(String userId, String jobId, Application application, MultipartFile resume) throws IOException {
        // Ensure upload folder exists
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Save resume file
        String resumeFileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
        String resumePath = uploadFolder.getAbsolutePath() + File.separator + resumeFileName;
        resume.transferTo(new File(resumePath));

        // Set application fields
        application.setJobId(jobId);
        application.setUserId(userId);
        application.setResumePath(resumePath);

        Application savedApp = applicationRepository.save(application);

        // Send confirmation email
        emailService.sendJobApplicationEmail(application.getEmail(), jobId, application.getName());

        return savedApp;
    }

    // Fetch applied jobs as JobPost objects
    public List<JobPost> getAppliedJobPosts(String userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);

        return applications.stream()
                .map(app -> jobRepository.findById(app.getJobId()).orElse(null))
                .filter(job -> job != null)
                .collect(Collectors.toList());
    }
}
