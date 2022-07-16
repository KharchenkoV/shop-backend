package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.BucketDto;
import com.example.demo.models.Bucket;
import com.example.demo.models.User;

public interface BucketService {
	Bucket createBucket(User user, List<Long> productIds);
	
	void addProducts(Bucket bucket, List<Long> productIds);
	
	void deleteProductById(Bucket bucket, Long productId);
	
	void deleteAllProductsById(Bucket bucket, Long productId);
	
	void cleanBucketByUsername(String username);
	
	BucketDto getBucketByUsername(String username);
}
