package com.designpattern;

public class SMSService implements MessageServiceInterface {

	@Override
	public void messageService() {
		System.out.println("SMS sent!");
	}

}
