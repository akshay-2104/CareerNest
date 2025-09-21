package com.akshay.CareerNest.controller;

import com.akshay.CareerNest.entity.JobPost;
import com.akshay.CareerNest.entity.Role;
import com.akshay.CareerNest.entity.User;
import com.akshay.CareerNest.repository.JobRepository;
import com.akshay.CareerNest.repository.RoleRepository;
import com.akshay.CareerNest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    // Signup endpoint
    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "USER") String roleType) {
        roleType = roleType.trim(); // <-- trim spaces and newline

        if(userRepository.findByUsername(username).isPresent()) {
            return "Username already exists!";
        }

        Role role = roleRepository.findByName("ROLE_" + roleType.toUpperCase());
        if(role == null) {
            return "Role not found!";
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // hash password
                .role(role)
                .build();

        userRepository.save(user);
        return "User registered successfully with role " + role.getName();

    }

    @GetMapping("/login")
    public Object login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "❌ Authentication failed!";
        }

        String username = authentication.getName();
        return jobRepository.findAll();
    }


}
