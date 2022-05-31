package ajbc.webservice.rest.api_demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.models.Student;

public class ServerThread extends Thread {

	private final int PORT;
	private boolean stopped;

	public ServerThread(int port) {
		PORT = port;
	}

	@Override
	public void run() {

		ServerSocket serverSocket = null;
		BufferedReader bufferReader = null;
		PrintWriter writer = null;
		StudentDBService studentDB = new StudentDBService();
		try {

			// start server
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started on port " + PORT);

			while (!stopped) {
				// 3 way handshake
				Socket clientSocket = serverSocket.accept();

				// update db when client connects
				synchronized (clientSocket) {
					Student student = new Student("Guy", "Tordjman", 99.9);
					studentDB.addStudent(student);
				}
				System.out.println(
						"client is connected " + clientSocket.getInetAddress() + " port " + clientSocket.getPort());

				// create reader
				bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				// reading data
				String line = bufferReader.readLine();
				System.out.println("client says: " + line);

				// sending data
				writer = new PrintWriter(clientSocket.getOutputStream(), true);
				writer.println("I must have called a thousand times");
			}

		} catch (IOException e) {
			System.err.println("Failed to start server on port " + PORT);
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (bufferReader != null)
				try {
					bufferReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void kill() {
		stopped = true;
	}
}
