package ajbc.learn.springboot.bootDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class BootDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootDemoApplication.class, args);
	}
}
