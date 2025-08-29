package com.akshay.CareerNest.entity;

//import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String role;
    private LocalDate joiningDate;    // e.g. 2025-09-15
    private String workType;          // Internship / Fulltime
    private Double packageLpa;        // e.g. 6.5

    @Column(length = 2000)
    private String description;

    private String driveLink;
}
