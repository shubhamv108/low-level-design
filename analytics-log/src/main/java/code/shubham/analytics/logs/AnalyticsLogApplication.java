package code.shubham.analytics.logs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class AnalyticsLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsLogApplication.class, args);
	}

}
