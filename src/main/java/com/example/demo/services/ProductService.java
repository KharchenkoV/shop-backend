package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.ProductDto;

public interface ProductService {
	List<ProductDto> getAll();
	void addToUserBucket(Long productId, String username);
	void deleteFromUserBucket(Long productId, String username);
	void deleteAllProductFromBucket(Long productId, String username);
}
