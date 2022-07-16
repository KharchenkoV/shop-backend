package com.example.demo.services;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.models.Bucket;
import com.example.demo.models.User;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BucketService bucketService;
	
	@Override
	public List<ProductDto> getAll() {
		return ProductDto.fromProductList(productRepository.findAll());
	}

	@Override
	@Transactional
	public void addToUserBucket(Long productId, String username) {
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new RuntimeException("User not found " + username);
		}
		
		Bucket bucket = user.getBucket();
		if(bucket == null) {
			Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
			user.setBucket(newBucket);
			userService.save(user);
		} else {
			bucketService.addProducts(bucket, Collections.singletonList(productId));
		}
		
	}
	
	
	@Override
	@Transactional
	public void deleteFromUserBucket(Long productId, String username) {
		Bucket bucket = loadBucketByUsername(username);
		if(bucket == null) {
			return;
		} else {
			bucketService.deleteProductById(bucket, productId);
		}
	}

	@Override
	public void deleteAllProductFromBucket(Long productId, String username) {
		Bucket bucket = loadBucketByUsername(username);
		if(bucket == null)
			return;
		else {
			bucketService.deleteAllProductsById(bucket, productId);
		}
	}
	
	private Bucket loadBucketByUsername(String username) {
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new RuntimeException("User not found " + username);
		}
		return user.getBucket();
	}

}
