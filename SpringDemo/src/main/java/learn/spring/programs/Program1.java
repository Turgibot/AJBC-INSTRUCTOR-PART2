package learn.spring.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import learn.spring.config.AppConfig1;
import learn.spring.dao.ProductDao;

public class Program1 {

	public static void main(String[] args) {
		
		// the dependency:
		ProductDao dao;
		
		// the spring container
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig1.class);
		
		System.out.println("-------------------------");
		
		dao = ctx.getBean("jdbcDao", ProductDao.class);
		System.out.println("This dao is an instance of : "+dao.getClass().getName());
		System.out.println("Reading from DB "+dao.count()+" products");
	
		//a singleton references to the same object 
		ProductDao dao1 = ctx.getBean("jdbcDao", ProductDao.class);
		System.out.println("dao == dao1 : "+(dao==dao1));
		
		// we can make multiple different prototypes objects
		ProductDao dao2 = ctx.getBean("mongoDao", ProductDao.class);
		System.out.println("This dao is an instance of : "+dao2.getClass().getName());
		
		ProductDao dao3 = ctx.getBean("mongoDao", ProductDao.class);
		System.out.println("dao2 == dao3 : "+(dao2==dao3));
		
		
		ctx.close();
	}

}
