package com.example.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/annotation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParamTest {
	
	@GET
	@Path("/matrixparam")
	public String getMatrixParam(@MatrixParam("matrixParam") String matrixParam) {
		return "Result : " + matrixParam;
	}
	
	@GET
	@Path("/headerParam")
	public String getHeaderParam(@HeaderParam("headerParam") String headerParam) {
		return "Result : " + headerParam;
	}
	
	@GET
	@Path("/cookieParam")
	public String getCookieParam(@CookieParam("cookieParam") String cookieParam) {
		return "Result : " + cookieParam;
	}
}
