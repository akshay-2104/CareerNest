package com.akshay.CareerNest.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private String name; // e.g. ROLE_USER, ROLE_ADMIN
}
