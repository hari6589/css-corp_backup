package com.designpattern;

public class Service {

	private MessageServiceInterface messageService;

	public void setMessageService(MessageServiceInterface messageService) {
		this.messageService = messageService;
	}
	
	public void sendMessage() {
		messageService.messageService();
	}
}
