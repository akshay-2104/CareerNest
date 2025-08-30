package com.akshay.CareerNest.entity;

//import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "jobposts")
public class JobPost {

    @Id
    private String id;

    private String companyName;
    private String role;
    private LocalDate joiningDate;    // e.g. 2025-09-15
    private String workType;          // Internship / Fulltime
    private Double packageLpa;        // e.g. 6.5
    private String description;
    private String driveLink;
}
