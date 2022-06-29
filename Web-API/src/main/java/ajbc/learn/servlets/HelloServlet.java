package ajbc.learn.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloServlet extends HttpServlet{

	@RequestMapping("/sayhello")
	public String SayHello() {
		System.out.println("In Say Hello");
		return "Hello from Server";
				
	}
}
