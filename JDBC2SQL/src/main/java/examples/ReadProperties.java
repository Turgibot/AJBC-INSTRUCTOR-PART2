package examples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ReadProperties {

	private static final String PROPERTIES_FILE = "demo.properties";

	public static void main(String[] args) {
		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		
		
		FileInputStream fileInputStream = null;
		
		try {
			Properties props = new Properties();
			fileInputStream = new FileInputStream(PROPERTIES_FILE);
			props.load(fileInputStream);
			
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			String serverAddress = props.getProperty("server_address");
			String port = props.getProperty("port");
			String databaseName = props.getProperty("db_name");
			String serverName = props.getProperty("server_name");
			
			DBConnectionString connectStr = new DBConnectionString(serverAddress, port, databaseName, serverName);
			connection = DriverManager.getConnection(connectStr.getUrl(), user, password);
			System.out.println("Connected to SQL Server " + connection.isValid(2));
			statement = connection.createStatement();
			String query = "select * from employees where id = 1001";
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1) + ",\t " + resultSet.getString("LastName") + ",\t"
						+ resultSet.getString("FirstName") + ",\t" + resultSet.getString(4) + ",\t"
						+ resultSet.getString(5) + ",\t" + resultSet.getFloat("salary"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
				fileInputStream.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
