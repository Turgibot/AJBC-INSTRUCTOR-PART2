package ajbc.webservice.rest.api_demo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.models.Student;

public class ServerSocketThread implements Runnable {

	private Socket clientSocket;
	private StudentDBService studentDB;
	public ServerSocketThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		studentDB = new StudentDBService();
	}

	@Override
	public void run() {

		try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
							
			Student student = new Student();
			studentDB.addStudent(student);
			System.out.println("added student with id %d".formatted(student.getID()));
			String line = bufferReader.readLine();
//			System.out.println("Thing says: " + line);
			
			// sending data
			writer.println("added student with id %d".formatted(student.getID()));
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

}
