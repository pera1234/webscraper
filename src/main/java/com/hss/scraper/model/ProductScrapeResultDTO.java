package com.hss.scraper.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An immutable dto
 * @author harneksandhar
 *
 */
public class ProductScrapeResultDTO {
	private List<Product> products = new ArrayList<>();
	private final float sum;
	
	public ProductScrapeResultDTO(List<Product> products, float sum) {
		super();
		this.products = products;
		this.sum = sum;
	}

	@JsonProperty("results")
	public List<Product> getProducts() {
		return Collections.unmodifiableList(new ArrayList(products));
	}
	@JsonProperty("total")
	public float getSum() {
		return sum;
	}
	
}
