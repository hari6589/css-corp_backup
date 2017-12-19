package com.test.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handleConflict(Exception e) {
		System.out.println("GlobalExceptionHandler - Exception!!!");
		e.printStackTrace();
	}
	
	@ExceptionHandler(IOException.class)
	public Map<String, String> handleConflictIOException(IOException e) {
		System.out.println("GlobalExceptionHandler - IOException");
		e.printStackTrace();
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", "IOException occured!");
		return output;
	}
	
}
