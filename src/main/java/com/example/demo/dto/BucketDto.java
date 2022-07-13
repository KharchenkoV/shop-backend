package com.example.demo.dto;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDto {
	private int amountProducts;
	private double sum;
	private List<BucketDetails> bucketDetails = new ArrayList<>();
	
	
	public void aggregate() {
		this.amountProducts = bucketDetails.size();
		this.sum = bucketDetails.stream()
				.map(BucketDetails::getSum)
				.mapToDouble(Double::doubleValue)
				.sum();
	}
}
