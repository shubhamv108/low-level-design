package code.shubham.pastebin;

import code.shubham.commons.annotations.SpringBootJpaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootJpaApplication
public class PasteBinApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasteBinApplication.class, args);
	}

}
