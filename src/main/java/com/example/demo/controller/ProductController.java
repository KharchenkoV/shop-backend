package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDto;
import com.example.demo.services.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductDto> getAllProducts(){
		return productService.getAll();
	}
	
	@GetMapping("/{id}/bucket")
	public ResponseEntity<?> addBucket(@PathVariable Long id, Principal principal) {
		if(principal == null) {
			return ResponseEntity.ok("You are not authorized!");
		}
		productService.addToUserBucket(id, principal.getName());
		return ResponseEntity.ok("Product has been added!");
	}
}
