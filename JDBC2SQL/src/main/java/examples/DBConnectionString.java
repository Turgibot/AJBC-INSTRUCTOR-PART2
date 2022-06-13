package examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionString {
	private String url;
	private String serverAddress;
	private String port;
	private String databaseName;
	private String serverName;

	public DBConnectionString(String serverAddress, String port, String databaseName, String serverName) {
		setServerAddress(serverAddress);
		setPort(port);
		setDatabaseName(databaseName);
		setServerName(serverName);
		buildUrl();
	}

	public Connection createConnection() {
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private void buildUrl() {
		this.url = "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + databaseName + ";servername="
				+ serverName + ";" + ";encrypt=false";
	}

	public String getUrl() {
		return url;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port2) {
		this.port = port2;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public static void main(String[] args) {
		String serverAddress = "localhost";
		String port = "1433";
		String databaseName = "NORTHWND";
		String serverName = "DESKTOP-PMASSCU";

		DBConnectionString connectExample = new DBConnectionString(serverAddress, port, databaseName, serverName);
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(connectExample.getUrl(), "TURGIBOT", "1234");
			System.out.println("Connected to SQL Server " + connection.isValid(2));
			statement = connection.createStatement();
			String query = "select * from Employees";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("EmployeeID") + ",\t " + resultSet.getString("LastName") + ",\t"
						+ resultSet.getString("FirstName") + ",\t" + resultSet.getString("Title") + ",\t"
						+ resultSet.getDate("BirthDate"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
