package com.example.demo.dto;

import java.math.BigDecimal;
import com.example.demo.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDetails {
	private String title;
	private Long productId;
	private String image;
	private BigDecimal price;
	private BigDecimal amount;
	private Double sum;
	
	public BucketDetails(Product product) {
		this.title = product.getTitle();
		this.productId = product.getId();
		this.price = product.getPrice();
		this.amount = new BigDecimal(1.0);
		this.sum = Double.valueOf(product.getPrice().toString());
		this.image = product.getImage();
	}
}
