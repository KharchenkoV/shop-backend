package com.example.demo.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BucketDetails;
import com.example.demo.dto.BucketDto;
import com.example.demo.models.Bucket;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.BucketRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class BucketServiceIml implements BucketService{
	
	@Autowired
	private BucketRepository bucketRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public Bucket createBucket(User user, List<Long> productIds) {
		Bucket bucket = new Bucket();
		bucket.setUser(user);
		List<Product> productList = getCollectRefProductsByIds(productIds);
		bucket.setProducts(productList);
		return bucketRepository.save(bucket);
	}
	
	private List<Product> getCollectRefProductsByIds(List<Long> productIds){
		return productIds.stream()
				.map(productRepository::getOne)
				.collect(Collectors.toList());
	}

	@Override
	public void addProducts(Bucket bucket, List<Long> productIds) {
		List<Product> products = bucket.getProducts();
		List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
		newProductList.addAll(getCollectRefProductsByIds(productIds));
		bucket.setProducts(newProductList);
		bucketRepository.save(bucket);
	}

	@Override
	public BucketDto getBucketByUsername(String username) {
		User user = userService.findByUsername(username);
		if(user == null || user.getBucket() == null) {
			return new BucketDto();
		}
		BucketDto bucketDto = new BucketDto();
		Map<Long, BucketDetails> mapByProductId = new HashMap<>();
		
		List<Product> products = user.getBucket().getProducts();
		for(Product product : products) {
			BucketDetails detail = mapByProductId.get(product.getId());
			if(detail == null) {
				mapByProductId.put(product.getId(), new BucketDetails(product));
			} else {
				detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
				detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
			}
		}
		
		bucketDto.setBucketDetails(new ArrayList<>(mapByProductId.values()));
		bucketDto.aggregate();
		
		return bucketDto;
	}

}
