/**
 * 
 */
package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aravindhan
 *
 */
@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String getHello() {
		return "Hello SpringBoot :)";
	}
}
