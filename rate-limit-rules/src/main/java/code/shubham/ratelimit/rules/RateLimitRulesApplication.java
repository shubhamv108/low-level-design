package code.shubham.ratelimit.rules;

import code.shubham.commons.annotations.SpringBootJpaApplication;
import code.shubham.ratelimit.rules.load.RuleLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@Slf4j
@SpringBootJpaApplication
public class RateLimitRulesApplication implements CommandLineRunner {

	@Autowired
	private RuleLoader ruleLoader;

	public static void main(String[] args) {
		SpringApplication.run(RateLimitRulesApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			this.ruleLoader.load();
		} catch (Exception exception) {
			log.error("Error loading rules into cache", exception.getMessage());
		}
	}
}
