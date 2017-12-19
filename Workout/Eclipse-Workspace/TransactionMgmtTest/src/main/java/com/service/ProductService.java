package com.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ProductDAO;
import com.model.Product;

@Component
public class ProductService {

	@Autowired
	ProductDAO productDAO;
	
	@Transactional
	public void add(Product product) {
		productDAO.persist(product);
	}
	
	@Transactional
	public void addAll(Collection<Product> products) {
		for(Product product : products) {
			productDAO.persist(product);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Product> getAll() {
		return productDAO.findAll();
	}
}
