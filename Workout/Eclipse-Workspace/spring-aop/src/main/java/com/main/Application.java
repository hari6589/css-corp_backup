package com.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.EmployeeService;

public class Application {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);
		
		System.out.println(employeeService.getEmployee().getName());
		
		employeeService.getEmployee().setName("Appu");
		
		employeeService.getEmployee().throwException();
		
	}

}
