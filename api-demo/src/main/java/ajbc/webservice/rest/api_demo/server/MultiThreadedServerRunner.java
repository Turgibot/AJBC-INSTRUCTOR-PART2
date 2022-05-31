package ajbc.webservice.rest.api_demo.server;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MultiThreadedServerRunner implements ServletContextListener {

	private final int PORT = 9090;
	ServerThread server;
	
	public void contextInitialized(ServletContextEvent event) {
		 server = new ServerThread(PORT);
		 server.start();
	}

	
	public void contextDestroyed(ServletContextEvent event) {
		server.kill();
	}

}
