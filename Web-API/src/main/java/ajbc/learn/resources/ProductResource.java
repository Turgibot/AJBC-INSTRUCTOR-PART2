package ajbc.learn.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Product;



@RequestMapping("/products")
@Controller
public class ProductResource {

	@Autowired
	ProductDao dao;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Product> getAllProducts() throws DaoException{
		return dao.getAllProducts();
	}
}
