package br.com.six2six.xstreamdsl.model;

import java.util.List;

public class Order {

	private Long id;
	private List<Product> products;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
