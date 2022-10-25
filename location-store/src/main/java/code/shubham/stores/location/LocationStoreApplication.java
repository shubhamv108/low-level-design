package code.shubham.stores.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class LocationStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationStoreApplication.class, args);
	}

}
