package ajbc.learn.program;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.MongoProductDao;
import ajbc.learn.dao.ProductDao;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner {

	public static void main(String[] args) throws DaoException {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// The dependency
		ProductDao dao1;
		
		// The spring container
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		dao1 = ctx.getBean("jdbcDao", ProductDao.class);
		
		System.out.println("----------------------------------");
		System.out.println("The number of products in the DB is "+dao1.count());
		System.out.println("----------------------------------");
		
		System.out.println("-------------AGAIN-----------------");
		System.out.println("The number of products in the DB is "+dao1.count());
		System.out.println("----------------------------------");
		
	}

}
