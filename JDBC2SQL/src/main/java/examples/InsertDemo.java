package examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDemo {

	public static void main(String[] args) {
		String serverAddress = "localhost";
		String port = "1433";
		String databaseName = "JDBC-DEMO";
		String serverName = "DESKTOP-PMASSCU";

		DBConnectionString connectStr = new DBConnectionString(serverAddress, port, databaseName, serverName);
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection(connectStr.getUrl(), "TURGIBOT", "1234");
			System.out.println("Connected to SQL Server " + connection.isValid(2));
			statement = connection.createStatement();
			String query = "Insert Into Employees\r\n" + "(lastName, firstName, email, department, salary)"
					+ "values('Tordjman','Guy', 'turgibot@gmail.com', 'Academy', 100000)";

			int rowsAffected = statement.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
