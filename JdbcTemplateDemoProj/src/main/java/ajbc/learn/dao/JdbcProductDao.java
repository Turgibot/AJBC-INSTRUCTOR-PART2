package ajbc.learn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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


	@Autowired(required = false)
	private JdbcTemplate template;
	
	private RowMapper<Product> prm = (rs, i) -> {
		Product p = new Product();
		p.setProductId(rs.getInt("productId"));
		p.setProductName(rs.getString("productName"));
		p.setSupplierId(rs.getInt("supplierId"));
		p.setCategoryId(rs.getInt("categoryId"));
		p.setQuantityPerUnit(rs.getString("quantityPerUnit"));
		p.setUnitPrice(rs.getDouble("unitPrice"));
		p.setUnitsInStock(rs.getInt("unitsInStock"));
		p.setUnitsOnOrder(rs.getInt("unitsOnOrder"));
		p.setReorderLevel(rs.getInt("reorderLevel"));
		p.setDiscontinued(rs.getInt("discontinued"));
		return p;
	};

	@Override
	public void addProduct(Product p) throws DaoException {
		String sql = "insert into products values (?,?,?,?,?,?,?,?,?,?)";
		template.update(sql, p.getProductId(), p.getProductName(), p.getSupplierId(), p.getCategoryId(),
				p.getQuantityPerUnit(), p.getUnitPrice(), p.getUnitsInStock(), p.getUnitsOnOrder(),
				p.getReorderLevel(), p.getDiscontinued());
	}

	@Override
	public void updateProduct(Product p) throws DaoException {
		String sql = "update products set productName=?, supplierId=?, categoryId=?, quantityPerUnit=?, "
				+ "unitPrice=?, unitsInStock=?, unitsOnOrder=?, reorderLevel=?, discontinued=? "
				+ "where productId=?";
		template.update(sql, p.getProductName(), p.getSupplierId(), p.getCategoryId(),
				p.getQuantityPerUnit(), p.getUnitPrice(), p.getUnitsInStock(), p.getUnitsOnOrder(),
				p.getReorderLevel(), p.getDiscontinued(), p.getProductId());
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		String sql = "Select * from products where productId=?";
		return template.queryForObject(sql, prm, productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		String sql = "update products set discontinued=1 where productId=?";
		template.update(sql, productId);
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		String sql = "select * from products";
		return template.query(sql, prm);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		String sql = "select * from products where unitPrice between ? and ?";
		return template.query(sql, prm, min, max);
	}

	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		String sql = "select * from products where category_id = ?";
		return template.query(sql, prm, categoryId);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		String sql = "select * from products where units_in_stock=0";
		return template.query(sql, prm);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		String sql = "select * from products where units_on_order>0";
		return template.query(sql, prm);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		String sql = "select * from products where discontinued=1";
		return template.query(sql, prm);
	}

	@Override
	public long count() throws DaoException {
		String sql = "select count(*) from products";
		return template.queryForObject(sql, Long.class);
	}

}
