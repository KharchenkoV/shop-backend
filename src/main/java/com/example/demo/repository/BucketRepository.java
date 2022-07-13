package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
	
}
