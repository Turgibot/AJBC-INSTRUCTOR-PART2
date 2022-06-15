package ajbc.jdbc.db.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ajbc.jdbc.models.Employee;

public class EmployeeDbService {

	public Employee addEmployee(Connection conn, Employee employee) {

		try (Statement statement = conn.createStatement()) {
			String query = "Insert Into Employees (lastName, firstName, email, department, salary)"
					+ "values('%s', '%s', '%s', '%s', %f)".formatted(employee.getLastName(), employee.getFirstName(),
							employee.getEmail(), employee.getDepartment(), employee.getSalary());
			if (statement.executeUpdate(query) == 1) {
				return employee;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Employee> addEmployees(Connection connection, List<Employee> employees) {
		
		String insertOp = "Insert Into Employees (lastName, firstName, email, department, salary) values(?, ?, ?, ?, ?)";
		String columnNames[] = new String[] { "id" };
		

		
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertOp, columnNames)) {
			
			connection.setAutoCommit(false); 
			
			for (Employee employee : employees) {
				preparedStatement.setString(1, employee.getLastName());
				preparedStatement.setString(2, employee.getFirstName());
				preparedStatement.setString(3, employee.getEmail());
				preparedStatement.setString(4, employee.getDepartment());
				preparedStatement.setFloat(5, employee.getSalary());
				preparedStatement.addBatch();
			}
			
			int[] rowsAffected = preparedStatement.executeBatch();
			int sum = 0;
			for (int i : rowsAffected) {
				sum += i;
			}
			if(sum!=employees.size())
				throw new SQLException("Transaction Cancelled");
			try(Statement statement = connection.createStatement()){
				List<Employee> newEmployees = new ArrayList<>(); 
				String countQuery = "select top (%d) * from Employees order by id desc".formatted(employees.size());
				ResultSet resultSet = statement.executeQuery(countQuery);
				while (resultSet.next()) {
					Employee emp = new Employee();
					emp.setId(resultSet.getInt(1));
					emp.setLastName(resultSet.getString(2));
					emp.setFirstName(resultSet.getString(3));
					emp.setEmail(resultSet.getString(4));
					emp.setDepartment(resultSet.getString(5));
					emp.setSalary(resultSet.getFloat(6));
					newEmployees.add(emp);
				}
				return newEmployees;
			}
		} catch (SQLException e) {
			try {
				System.err.println(e.getMessage());
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return null;
	}
	public Employee getEmployee(Connection conn, int id) {
		ResultSet resultSet = null;
		Employee emp = null;
		try (Statement statement = conn.createStatement()) {
			String query = "select * from  Employees where id = %d".formatted(id);
			resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				emp = new Employee();
				emp.setId(resultSet.getInt(1));
				emp.setLastName(resultSet.getString(2));
				emp.setFirstName(resultSet.getString(3));
				emp.setEmail(resultSet.getString(4));
				emp.setDepartment(resultSet.getString(5));
				emp.setSalary(resultSet.getFloat(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return emp;
	}

	public Employee updateEmployee(Connection conn, Employee employee) {
		Employee currEmp = getEmployee(conn, employee.getId());
		if (!employee.equals(currEmp)) {
			try (Statement statement = conn.createStatement()) {
				String query = "Update Employees set lastName='%s', firstName='%s', email='%s', "
						.formatted(employee.getLastName(), employee.getFirstName(), employee.getEmail())
						+ "department='%s', salary=%f where id = %d".formatted(employee.getDepartment(),
								employee.getSalary(), employee.getId());
				if (statement.executeUpdate(query) == 1) {
					return employee;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("No change made to DB");
		return null;
	}

	public Employee deleteEmployee(Connection conn, int id) {
		Employee currEmp = getEmployee(conn, id);
		if (currEmp != null) {
			try (Statement statement = conn.createStatement()) {
				String query = "delete from Employees where id = %d".formatted(id);
				if (statement.executeUpdate(query) == 1) {
					return currEmp;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("No change made to DB");
		return null;
	}
}
