package ajbc.learn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

//	public HelloController() {
//		System.out.println(" - - - - - hi guy - - - - - -");
//	}
	
	@RequestMapping("/hello-mvc")
	public String sayHello() {
		System.out.println("HelloController.sayHello() called");
		return "/WEB-INF/pages/hello.jsp";
	}
}
