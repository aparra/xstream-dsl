package br.com.six2six.xstreamdsl.model;

import java.util.List;

public class Order {

	private long id;
	private List<Product> products;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
