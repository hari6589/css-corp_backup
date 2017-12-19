package com.aem;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service({ SlingAllMethodsServlet.class })
@SlingServlet(paths = { 
		"/bsro/services/myfirestone/appointment"
}, methods = "GET")

public class Hello extends SlingAllMethodsServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	static final String APPOINTMENT_CONFIRM_PATH = "confirm";
	
	private static final Logger log = LoggerFactory.getLogger(Hello.class);
	
	public void doGet(SlingHttpServletRequest request,
			  SlingHttpServletResponse response) throws ServletException,IOException {
		log.info("sayHello! - Get - info");
		log.debug("sayHello! - Get - Debug");
		log.error("sayHello! - Get - Error");
	}
	
	public String sayHello() {
		log.info("sayHello! - info");
		log.debug("sayHello! - Debug");
		log.error("sayHello! - Error");
		return null;
	}
}
