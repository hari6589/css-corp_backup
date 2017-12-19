package com.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class GreetingController {

	@RequestMapping("/greeting")
	public Greeting getGreeting() {
		System.out.println("Test!");
		return new Greeting(1, "Hello Hari!");
	}
}
