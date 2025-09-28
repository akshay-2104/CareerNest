package com.akshay.CareerNest.controller;

import com.akshay.CareerNest.entity.JobPost;
import com.akshay.CareerNest.entity.Role;
import com.akshay.CareerNest.entity.User;
import com.akshay.CareerNest.repository.JobRepository;
import com.akshay.CareerNest.repository.RoleRepository;
import com.akshay.CareerNest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup endpoint
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "USER") String roleType) {

        if (userRepository.findByUsername(username).isPresent()) {
            return "❌ Username already exists!";
        }

        Role role = roleRepository.findByName("ROLE_" + roleType.toUpperCase());
        if (role == null) {
            return "❌ Role not found!";
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        userRepository.save(user);
        return "✅ User registered successfully with role " + role.getName();
    }

    // Login endpoint – only shows available jobs
    @GetMapping("/login")
    public Object login(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "❌ Authentication failed!";
        }

        String email = authentication.getName(); // Spring Security username
        User user = userRepository.findByUsername(email).orElse(null);

        if (user == null) {
            return "❌ User not found!";
        }

        String role = user.getRole().getName();

        if ("ROLE_USER".equals(role)) {
            List<JobPost> availableJobs = jobRepository.findAll();

            return Map.of(
                    "message", "✅ Login successful",
                    "role", role,
                    "availableJobs", availableJobs
            );
        }

        if ("ROLE_ADMIN".equals(role)) {
            Map<String, Object> adminOptions = new HashMap<>();
            adminOptions.put("message", "Welcome Admin " + email);
            adminOptions.put("endpoints", new String[]{
                    "/recruiter/private/jobs [POST] → Create Job Post",
                    "/recruiter/private/jobs [GET] → Get All Job Posts",
                    "/recruiter/private/jobs/{id} [PUT] → Update Job Post",
                    "/recruiter/private/jobs/{id} [DELETE] → Delete Job Post",
                    "/recruiter/private/jobs/count/{id} [GET] → Count Applicants for Job"
            });
            return adminOptions;
        }

        return "❌ Unknown role!";
    }
}
