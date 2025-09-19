package com.akshay.CareerNest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendJobApplicationEmail(String toEmail, String jobId, String applicantName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Application Received for Job ID: " + jobId);
        message.setText("Hello " + applicantName + ",\n\n" +
                "Your application for Job ID " + jobId + " has been successfully submitted.\n" +
                "We will review your profile and get back to you soon.\n\n" +
                "Best regards,\nCareerNest Team");

        mailSender.send(message);
    }
}
