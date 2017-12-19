package com.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("/sample")
	public Map<String, String> sample(@RequestParam(value="name", defaultValue="World") String name) {
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", "Hello " + name);
		LOGGER.trace("doStuff needed more information - {}", 1);
		LOGGER.debug("doStuff needed to debug - {}", 2);
		LOGGER.info("doStuff took input - {}", 3);
		LOGGER.warn("doStuff needed to warn - {}", 4);
		LOGGER.error("doStuff encountered an error with value - {}", 5);
		return output;
	}
	
	@RequestMapping("/exception")
	public Map<String, String> exception(@RequestParam(value="name", defaultValue="World") String name) throws Exception {
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", "Hello " + name);
		throw new Exception();
		//return output;
	}
	
	@RequestMapping("/ioexception")
	public Map<String, String> ioexception(@RequestParam(value="name", defaultValue="World") String name) throws IOException {
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", "Hello " + name);
		throw new IOException();
		//return output;
	}
	
}
