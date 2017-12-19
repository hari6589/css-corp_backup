package org.ari.javabrains.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

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
	
	@GET
	@Path("/setCookie/{myCookie}")
	public Response setCookie(@Context Request request, @PathParam("myCookie") String myCookie) {
		//return "Result : " + myCookie;
//		JSONObject obj = new JSONObject();
//		obj.put("name", "mkyong.com");
//		obj.put("age", new Integer(100));
//
//		JSONArray list = new JSONArray();
//		list.add("msg 1");
//		list.add("msg 2");
//		list.add("msg 3");
//
//		obj.put("messages", list);
		NewCookie cookie = new NewCookie("myName", myCookie, "/", "", "My Comment", 100, false);
		return Response.ok("OK").cookie(cookie).build();
	}
	
	@GET
	@Path("/getCookie")
	public Response getCookie(@CookieParam("myName") String myCookie) {
		System.out.println("Cookie ::: " + myCookie);
		return Response.ok(myCookie).build();
	}
}
