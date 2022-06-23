package ajbc.learn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component(value = "jdbcDao")
@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {


	@Autowired
	private JdbcTemplate template;

	
	RowMapper<Product> rowMapper = (rs, rowNum) -> {
		Product prod = new Product();
		prod.setCategoryId(rs.getInt(1));
		//continue
		return prod;

	};

	//CRUD
	
	@Override
	public void addProduct(Product product) throws DaoException {
		
		ProductDao.super.addProduct(product);
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		// TODO Auto-generated method stub
		ProductDao.super.updateProduct(product);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		// TODO Auto-generated method stub
		return ProductDao.super.getProduct(productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		// TODO Auto-generated method stub
		ProductDao.super.deleteProduct(productId);
	}

	

	
	

}
