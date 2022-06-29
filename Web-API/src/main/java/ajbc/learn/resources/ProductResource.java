package ajbc.learn.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;

@RequestMapping("/products")
@RestController
public class ProductResource {

	@Autowired
	ProductDao dao;

//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<Product>> getAllProducts() throws DaoException {
//		List<Product> list = dao.getAllProducts();
//		if (list == null)
//			return ResponseEntity.notFound().build();
//
//		return ResponseEntity.ok(list);
//	}

//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<Product>> getProductsByRange(@RequestParam Optional<Double> min,
//			@RequestParam Optional<Double> max) throws DaoException {
//		List<Product> list;
//		if (min.isPresent() && max.isPresent())
//			list = dao.getProductsByPriceRange(min.get(), max.get());
//		else
//			list = dao.getAllProducts();
//		
//		if (list == null)
//			return ResponseEntity.notFound().build();
//
//		return ResponseEntity.ok(list);
//	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductsByRange(@RequestParam Map<String, String> map) throws DaoException {
		List<Product> list;
		Set<String> keys = map.keySet();
		
		if (keys.contains("min") && keys.contains("max"))
			list = dao.getProductsByPriceRange(Double.parseDouble(map.get("min")), Double.parseDouble(map.get("max")));
		else
			list = dao.getAllProducts();
		
		if (list == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/{id}")
	public ResponseEntity<?> getProductsById(@PathVariable Integer id) {
		
		Product prod;
		try {
			prod = dao.getProduct(id);
			return ResponseEntity.ok(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to get product with id "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product prod) {
		
		try {
			dao.addProduct(prod);
			prod = dao.getProduct(prod.getProductId());
			return ResponseEntity.status(HttpStatus.CREATED).body(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to add product to db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product prod, @PathVariable Integer id) {
		
		try {
			prod.setProductId(id);
			dao.updateProduct(prod);
			prod = dao.getProduct(prod.getProductId());
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to update product in db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
	

	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		
		try {
			Product prod = dao.getProduct(id);
			dao.deleteProduct(id);
			prod = dao.getProduct(id);
			return ResponseEntity.status(HttpStatus.OK).body(prod);
		} catch (DaoException e) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setData(e.getMessage());
			errorMessage.setMessage("failed to delete product from db");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMessage);
		}
	}
}