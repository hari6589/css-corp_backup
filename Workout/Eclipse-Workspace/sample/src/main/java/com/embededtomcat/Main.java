package com.embededtomcat;

public class Main {
	public static void main(String[] args) {
		java.util.UUID uuid = null;
		uuid = java.util.UUID.randomUUID();
		System.out.println(uuid);
	}
}

/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class Main {

  public static void main(String[] args)
  throws LifecycleException, InterruptedException, ServletException {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

    Tomcat.addServlet(ctx, "hello", new HttpServlet() {
      protected void service(HttpServletRequest req, HttpServletResponse resp) 
      throws ServletException, IOException {
        Writer w = resp.getWriter();
        w.write("Hello, World!");
        w.flush();
      }
    });
    ctx.addServletMapping("/*", "hello");

    tomcat.start();
    tomcat.getServer().await();
  }

}*/