package examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransactionDemo {
	private static final int TIMEOUT = 2;

	public static void main(String[] args) {
		String serverAddress = "localhost";
		String port = "1433";
		String databaseName = "JDBC-DEMO";
		String serverName = "DESKTOP-PMASSCU";
		String user = "TURGIBOT";
		String password = "1234";
		DBConnectionString connectStr = new DBConnectionString(serverAddress, port, databaseName, serverName);
		String url = connectStr.getUrl();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			// 1 create a connection and set connection auto commit to false
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
			
			System.out.println("Connected to SQL Server " + connection.isValid(TIMEOUT));
			// 2 create query string
			String updateStr = "update Employees set email=? , firstName=? where id=?";
			// 3 create a statement
			preparedStatement = connection.prepareStatement(updateStr);
			// 4.1 set parameters
			preparedStatement.setString(1, "10@there.com");
			preparedStatement.setString(2, "aaaa");
			preparedStatement.setInt(3, 1009);

			// add to batch
			preparedStatement.addBatch();

			// 4.2 set parameters
			preparedStatement.setString(1, "20@there.com");
			preparedStatement.setString(2, "bbbbb");
			preparedStatement.setInt(3, 1010);

			// add to batch
			preparedStatement.addBatch();

			// 4.3 set parameters
			preparedStatement.setString(1, "30@there.com");
			preparedStatement.setString(2, "cccc");
			preparedStatement.setInt(3, 1011);

			// add to batch
			preparedStatement.addBatch();

			// execute
			int[] rowsAffected = preparedStatement.executeBatch();

			for (int i : rowsAffected) {
				if (i == 0) {
					throw new SQLException("Something is wrong with the data");
				}
				else
					System.out.println(i+" Rows changed");

			}
		
			// if everything is okay - commit changes
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
				System.out.println("Rolling DB back "+ e.getMessage());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		} finally {
			try {
				connection.close();
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	private static void printEmployee(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			System.out.println("----------------------------------------------------------------------------------");
			System.out.println(resultSet.getInt(1) + ",\t " + resultSet.getString(2) + ",\t" + resultSet.getString(3)
					+ ",\t" + resultSet.getString(4) + ",\t" + resultSet.getString(5) + ",\t" + resultSet.getFloat(6));
		}
	}
}
