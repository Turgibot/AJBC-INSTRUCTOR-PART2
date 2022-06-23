package learn.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import learn.spring.dao.JDBCProductDao;
import learn.spring.dao.MongoProductDao;

@Configuration
public class AppConfig1 {
	public AppConfig1() {
		System.out.println("AppConfig1 instantiated");
	}

	// for lazy instantiation
	// @Lazy
	@Scope("singleton")
	@Bean
	public JDBCProductDao jdbcDao() {
		System.out.println("AppConfig1.jdbcDao instantiated");
		return new JDBCProductDao();
	}
	@Scope("prototype")
	@Bean
	public MongoProductDao mongoDao() {
		System.out.println("AppConfig1.mongoDao instantiated");
		return new MongoProductDao();
	}
}
