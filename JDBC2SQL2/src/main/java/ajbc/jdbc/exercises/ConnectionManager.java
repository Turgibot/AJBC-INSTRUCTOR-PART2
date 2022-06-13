package ajbc.jdbc.exercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private String url;
	private String serverAddress;
	private String port;
	private String databaseName;
	private String serverName;
	private String userName;
	private String password;

	public ConnectionManager(String serverAddress, String port, String databaseName, String serverName, String userName,
			String password) {
		this.serverAddress = serverAddress;
		this.port = port;
		this.databaseName = databaseName;
		this.serverName = serverName;
		this.userName = userName;
		this.password = password;
		buildUrl();
	}

	public void buildUrl() {
		this.url = "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + databaseName + ";servername="
				+ serverName + ";" + ";encrypt=false";
	}

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, userName, password);
	}

	public static void main(String[] args) {
		String serverAddress = "localhost";
		String port = "1433";
		String databaseName = "JDBC-DEMO";
		String serverName = "DESKTOP-PMASSCU";
		String userName = "turgibot";
		String password = "1234";

		try (Connection connection = new ConnectionManager(serverAddress, port, databaseName, serverName, userName, password).connect();){
			System.out.println("Connected");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
