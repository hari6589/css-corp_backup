package com.designpattern;

public class EmailService implements MessageServiceInterface {

	@Override
	public void messageService() {
		System.out.println("Email sent!");
	}

}
