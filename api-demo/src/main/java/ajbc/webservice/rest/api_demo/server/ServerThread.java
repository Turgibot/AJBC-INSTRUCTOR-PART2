package ajbc.webservice.rest.api_demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.models.Student;

public class ServerThread extends Thread {

	private final int PORT;
	private ExecutorService executorService;

	public ServerThread(int port) {
		this.PORT = port;
		executorService = Executors.newCachedThreadPool();
	}

	@Override
	public void run() {

		try (ServerSocket serverSocket = new ServerSocket(PORT);) {

			System.out.println("Game Server started on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				executorService.execute(new ServerSocketThread(clientSocket));
			}
		} catch (IOException e) {
			System.err.println("Failed to start server on port " + PORT);
			e.printStackTrace();
		}
	}

	public void kill() {
		executorService.shutdown();
		try {
			executorService.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
