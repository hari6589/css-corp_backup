package com;

import java.util.Arrays;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import com.model.Product;
import com.service.ProductService;

public class SpringORMMain {

	/*
	 * Spring ORM example – JPA, Hibernate, Transaction
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		ProductService productService = ctx.getBean(ProductService.class);
		
		productService.add(new Product(1, "Light"));
		productService.add(new Product(2, "Fan"));
		
		System.out.println("Results : " + productService.getAll());
		
		try {
			//productService.addAll(Arrays.asList(new Product(3, "Fridge"), new Product(4, "Television"), new Product(1, "WashingMachine")));
			productService.addAll(Arrays.asList(new Product(3, "Fridge"), new Product(4, "Television")));
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		System.out.println("Results : " + productService.getAll());
		
		ctx.close();
	}
	
}
