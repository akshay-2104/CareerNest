package com.akshay.CareerNest.repository;

import com.akshay.CareerNest.entity.JobPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<JobPost, String> {
}
