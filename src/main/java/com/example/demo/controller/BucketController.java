package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BucketDto;
import com.example.demo.services.BucketService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bucket")
public class BucketController {
	@Autowired
	private BucketService bucketService;
	
	@GetMapping
	public BucketDto aboutBucket(Principal principal) {
		BucketDto bucketDto = new BucketDto();
		if(principal == null) {
			return bucketDto;
		} else {
			bucketDto = bucketService.getBucketByUsername(principal.getName());
		}
		return bucketDto;
	}
	

	@GetMapping("/clean")
	public ResponseEntity<?> cleanBucket(Principal principal) {
		if(principal == null) {
			return ResponseEntity.ok("You are not authorized!");
		}
		bucketService.cleanBucketByUsername(principal.getName());
		return ResponseEntity.ok("Bucket has been cleaned!");
	}
}
