package com.akshay.CareerNest.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
    private String password;

    @DBRef
    private List<JobPost> appliedJobs = new ArrayList<>();

    @DBRef
    private Role role;  // single role (USER or ADMIN)
}
