package com.designpattern;

public class DeptInject {

	public static void main(String[] args) {
		
		Service service = new Service();
		
		// Service (Email or SMS) are injected into Service
		service.setMessageService(new EmailService());
		service.sendMessage();
		
		service.setMessageService(new SMSService());
		service.sendMessage();
		
	}

}
