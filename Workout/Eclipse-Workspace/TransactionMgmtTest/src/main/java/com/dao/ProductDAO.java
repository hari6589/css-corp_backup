package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.model.Product;

@Component
public class ProductDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void persist(Product product) {
		em.persist(product);
	}
	
	public List<Product> findAll() {
		return em.createQuery("SELECT p FROM Product p").getResultList();
	}
}
