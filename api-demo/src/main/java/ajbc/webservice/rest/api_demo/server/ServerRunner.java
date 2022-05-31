package ajbc.webservice.rest.api_demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.models.Student;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServerRunner implements ServletContextListener {
	private final int PORT = 9090;
	private ServerThread thread = new ServerThread(PORT);
	public void contextInitialized(ServletContextEvent event) {
		thread.start();
	}

	
	public void contextDestroyed(ServletContextEvent event) {
		thread.kill();
	}

}
