package ajbc.learn.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.ErrorResponse;
import ajbc.learn.models.Product;

@RequestMapping("/products")
@RestController

public class ProductResource {

	@Autowired
	ProductDao htDao;

//	@RequestMapping(method = RequestMethod.GET)
//	public List<Product> getAllProducts() throws DaoException {
//		return htDao.getAllProducts();
//	}

//	@RequestMapping()
//	public ResponseEntity<List<Product>> getAllProducts() throws DaoException {
//		List<Product> products = htDao.getAllProducts();
//		if (products == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(products);
//	}

	// between1
//		@RequestMapping(method = RequestMethod.GET)
//		public ResponseEntity<?> getProducts(@RequestParam(defaultValue = "99.0") Optional<Double> min, @RequestParam Optional<Double> max)
//				throws DaoException {
//			List<Product> products = null;
//			if(min.isPresent() && max.isPresent()) {
//				products= htDao.getProductsByPriceRange(min.get(), max.get());
//			}else
//				products = htDao.getAllProducts();
//			
//			if (products == null) {
//				return ResponseEntity.notFound().build();
//			}
//			return ResponseEntity.ok(products);
//
//		}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getProducts(@RequestParam(required = false) Map<String, String> map) throws DaoException {
		List<Product> products = null;
		Set<String> keys = map.keySet();
		if (keys.contains("min") && keys.contains("max")) {
			Double min = Double.parseDouble(map.get("min"));
			Double max = Double.parseDouble(map.get("max"));
			products = htDao.getProductsByPriceRange(min, max);
		} else
			products = htDao.getAllProducts();

		if (products == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(products);

	}

//  1
//	@RequestMapping( path = "/{id}")
//	public Product getProductById(@PathVariable("id") Integer id) throws DaoException {
//
//		Product prod = htDao.getProduct(id);
//		return prod;
//
//	}
//	
//	2
//	@RequestMapping(path = "/{id}")
//	public ResponseEntity<Product> getProductById(@PathVariable Integer id) throws DaoException {
//
//		Product pr = htDao.getProduct(id);
//		if (pr == null) {
//			
//			return ResponseEntity.notFound().build();
//		}
//
//		return ResponseEntity.ok(pr);
//
//	}
//	

//	3
	@RequestMapping(path = "/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Integer id) throws DaoException {

		Product prod = htDao.getProduct(id);
		if (prod == null) {
			ErrorResponse er = new ErrorResponse();
			er.setMessage("No product found!");
			er.setData(id);
			return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(prod);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product pr) {
		try {
			htDao.addProduct(pr);
			pr = htDao.getProduct(pr.getProductId());
			return ResponseEntity.ok(pr);
		} catch (DaoException ex) {
			ErrorResponse er = new ErrorResponse();
			er.setData(ex.getClass().getName());
			er.setMessage(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product pr) {
		try {
			pr.setProductId(id);
			htDao.updateProduct(pr);
			pr = htDao.getProduct(pr.getProductId());
			return ResponseEntity.ok(pr);
		} catch (Exception ex) {
			ErrorResponse er = new ErrorResponse();
			er.setData(ex.getClass().getName());
			er.setMessage(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
		}
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		try {
			Product pr = htDao.getProduct(id);
//			if (pr == null) {
//				ErrorResponse er = new ErrorResponse();
//				er.setMessage("No product found!");
//				er.setData(id);
//				return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
//			}

			htDao.deleteProduct(id);
			pr = htDao.getProduct(id);
			return ResponseEntity.ok(pr);
		} catch (Exception ex) {
			ErrorResponse er = new ErrorResponse();
			er.setData(ex.getClass().getName());
			er.setMessage(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
		}
	}
}
