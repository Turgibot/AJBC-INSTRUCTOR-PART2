package ajbc.learn.program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.MongoProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner2 {

	public static void main(String[] args) {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);

		RowMapper<Category> rowMapper = (rs, rowNum) -> {
			Category cat = new Category();
			cat.setCategoryId(rs.getInt(1));
			cat.setCategoryName(rs.getString("categoryName"));
			cat.setDescription(rs.getString("description"));
			cat.setPicture(rs.getBytes("picture"));
			return cat;

		};

		// CRUD
		// insertNewShipper(template);
		// updateShipperPhone(template, 5, "(012) 345-6789");

		// query that return a single value

		// printNumProducts(template);
		// printShipperName(template, 5);
		// printCityOfCustomerById(template, "FISSA");

		// printProductDetails(template, 3);

		// printAnyOrderofEmployeeById(template, 54);

		// printAllShippers(template);
		// printAllShipperNames(template);

//		printCategoryById(template, rowMapper, 5);
//		printAllCategories(template, rowMapper);
	}

	private static void printAllCategories(JdbcTemplate template, RowMapper<Category> rowMapper) {
		List<Category> list = template.query("select * from categories", rowMapper);
		for (Category c : list) {
			System.out.println(c);
		}

	}

	private static void printCategoryById(JdbcTemplate template, RowMapper<Category> rowMapper, int id) {
		String sql = "select * from categories where categoryID = ?";
		Category cat = template.queryForObject(sql, rowMapper, id);
		System.out.println(cat);
	}

	static void printAllShipperNames(JdbcTemplate template) {
		String sql = "select companyName from shippers";
		List<String> list = template.queryForList(sql, String.class);
		for (String name : list) {
			System.out.println(name);
		}
	}

	private static void printAllShippers(JdbcTemplate template) {
		String sql = "select * from shippers";
		List<Map<String, Object>> data = template.queryForList(sql);
		for (Map<String, Object> map : data) {
			System.out.println(map);
		}
	}

	private static void printAnyOrderofEmployeeById(JdbcTemplate template, int employeeId) {
		String sql = "select * from orders where employeeId = ?";
		List<Map<String, Object>> data = template.queryForList(sql, employeeId);
		if (data != null && data.size() > 0)
			System.out.println(data.get(0));
		else
			System.out.println("nothing");
	}

	private static void printProductDetails(JdbcTemplate template, int id) {
		String sql = "select * from products where productId = ?";
		Map<String, Object> data = template.queryForMap(sql, id);
		System.out.println(data.keySet());
		System.out.println(data.values());
	}

	private static void printCityOfCustomerById(JdbcTemplate template, String id) {
		String sql = "select city from customers where customerId = ?";
		String city = template.queryForObject(sql, String.class, id);
		System.out.println("city is " + city);
	}

	private static void printShipperName(JdbcTemplate template, int id) {
		String sql = "select companyName from shippers where shipperId = ?";
		String name = template.queryForObject(sql, String.class, id);
		System.out.println("Shipper company name is " + name);
	}

	private static void printNumProducts(JdbcTemplate template) {
		String sql = "select count(*) from products";
		int count = template.queryForObject(sql, int.class);
		System.out.println("count : " + count);
	}

	private static void updateShipperPhone(JdbcTemplate template, int id, String phone) {
		String sql = "update shippers set phone = ? where shipperId = ?";
		int rowsAffected = template.update(sql, phone, id);
		System.out.println("rows affected : " + rowsAffected);
	}

	private static void insertNewShipper(JdbcTemplate template) {
		String sql = "insert into shippers values (?, ?)";
		int rowsAffected = template.update(sql, "Turgi Imports", "123-456-9878");
		System.out.println("rows affected : " + rowsAffected);
	}

}
