package ajbc.learn.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ajbc.learn.models.Category;
import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@EnableTransactionManagement
@ComponentScan(basePackages = { "ajbc.learn.dao","ajbc.learn.aspects" })
@EnableAspectJAutoProxy
@Configuration
@PropertySource("classpath:jdbc.properties")
public class AppConfig {
	private static final int INIT_SIZE = 10, MAX_SIZE = 100, MAX_WAIT = 500, MAX_IDLE = 50, MIN_IDLE = 2;

	@Value("${server_address}")
	private String serverAddress;
	@Value("${port}")
	private String port;
	@Value("${db_name}")
	private String databaseName;
	@Value("${server_name}")
	private String serverName;
	@Value("${user}")
	private String userName;
	@Value("${password}")
	private String password;
	@Value("${driver_class_name}")
	private String driverClassName;
	@Value("${dialect}")
	private String dialect;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		// credentials
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setUrl(url());

		// preferences
		dataSource.setInitialSize(INIT_SIZE);
		dataSource.setMaxTotal(MAX_SIZE);
		dataSource.setMaxWaitMillis(MAX_WAIT);
		dataSource.setMaxIdle(MAX_IDLE);
		dataSource.setMinIdle(MIN_IDLE);

		return dataSource;
	}


	@Bean
	
	public LocalSessionFactoryBean sessionFactory( DataSource dataSource) {
		
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(dataSource);
		//add mapped classes
		factory.setAnnotatedClasses(Category.class, Supplier.class, Product.class);
		
		// add properties to session
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", dialect);
		props.setProperty("hibernate.show_sql", "false");
		props.setProperty("hibernate.format_sql", "true");
		
		factory.setHibernateProperties(props);
		
		return factory;
	}
	
	
	@Bean 
	public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
		
	}
	
	@Bean
	public HibernateTransactionManager tranactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
	


	private String url() {
		return "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + databaseName + ";servername="
				+ serverName + ";" + ";encrypt=false";
	}
}
