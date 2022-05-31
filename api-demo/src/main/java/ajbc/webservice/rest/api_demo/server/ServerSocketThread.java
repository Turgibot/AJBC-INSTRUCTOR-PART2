package ajbc.webservice.rest.api_demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.models.Student;

public class ServerSocketThread implements Runnable {

	private Socket clientSocket;
	private boolean stopped;

	public ServerSocketThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {

		try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
							
			System.out.println(
					"Thing is connected " + clientSocket.getInetAddress() + " port " + clientSocket.getPort());
			// reading data
			String line = bufferReader.readLine();
			System.out.println("Thing says: " + line);
			
			// sending data
			writer.println("processing result done ");
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	public void kill() {
		stopped = true;
	}
}
