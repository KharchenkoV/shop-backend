package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private Long id;
	private String title;
	private BigDecimal price;
	private String image;
	
	public static List<ProductDto> fromProductList(List<Product> products) {
		List<ProductDto> productsDto = new ArrayList<>();
		for(Product product : products) {
			ProductDto productDto = new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getImage());
			productsDto.add(productDto);
		}
		return productsDto;
	}
}
