package ajbc.learn.program;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.HibernateTemplateProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;
import ajbc.learn.models.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner3 {

	public static void main(String[] args) throws DaoException {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);) 
		{
			
			ProductDao dao = ctx.getBean("htDao", ProductDao.class);
			
			List<Product> products = dao.getAllProducts();
			System.out.println(products.size());
			
			Double min = 50.0;
			Double max = 200.0;
			products = dao.getProductsByPriceRange(min, max);
//			products.forEach(System.out::println);
			
			
			products = dao.getProductsByPriceRange(max, min);
//			products.forEach(System.out::println);
			System.out.println(products.size());
			
			Product product = dao.getProduct(1);
			System.out.println(product);
			
			product.setUnitPrice(product.getUnitPrice()+1);
			
//			try{
//				
//				dao.updateProduct(product);
//			}
//			catch(DaoException e ){
//				System.out.println(e);
//			}
			
			System.out.println("Continute till end of main...");
		}
	}

}
