import java.sql.Connection;
import java.sql.SQLException;

import ajbc.jdbc.db.services.EmployeeDbService;
import ajbc.jdbc.exercises.ConnectionManager;
import ajbc.jdbc.models.Employee;

public class Runner {

	public static void main(String[] args) {

		String serverAddress = "localhost";
		String port = "1433";
		String databaseName = "JDBC-DEMO";
		String serverName = "DESKTOP-PMASSCU";
		String userName = "turgibot";
		String password = "1234";

		try (Connection connection = new ConnectionManager(serverAddress, port, databaseName, serverName, userName,
				password).connect();) {
			System.out.println("Connected");

//			add3Emps(connection);
//			getAndPrintEmp(connection, 1012);
//			update3Emps(connection);
			delete2Emps(connection);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void delete2Emps(Connection connection) {
		EmployeeDbService dbService = new EmployeeDbService();
		System.out.println(dbService.deleteEmployee(connection, 1002));
//		dbService.deleteEmployee(connection, 1001);
	}

	private static void update3Emps(Connection connection) {
		Employee emp1 = new Employee(1014, "SSShai", "Habshush", "g@abc.com", "Food", 22330);
		Employee emp2 = new Employee(1015, "HHHaim", "Banai", "jaim@abc.com", "Culture", 22330);
		Employee emp3 = new Employee(1016, "SSShir", "Simon", "simon@abc.com", "Health", 99999);

		EmployeeDbService dbService = new EmployeeDbService();
		dbService.updateEmployee(connection, emp1);
		dbService.updateEmployee(connection, emp2);
		dbService.updateEmployee(connection, emp3);
	}

	private static void getAndPrintEmp(Connection connection, int id) {
		EmployeeDbService dbService = new EmployeeDbService();
		Employee emp = dbService.getEmployee(connection, id);
		System.out.println(emp);
	}

	private static void add3Emps(Connection connection) {
		Employee emp1 = new Employee("Shai", "Habshush", "g@ggg.com", "Food", 22330);
		Employee emp2 = new Employee("Haim", "Banai", "jaim@ggg.com", "Culture", 22330);
		Employee emp3 = new Employee("Shir", "Simon", "simon@ggg.com", "Health", 22330);

		EmployeeDbService dbService = new EmployeeDbService();
		dbService.addEmployee(connection, emp1);
		dbService.addEmployee(connection, emp2);
		dbService.addEmployee(connection, emp3);
	}

}
