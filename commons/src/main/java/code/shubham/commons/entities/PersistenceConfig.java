package code.shubham.commons.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
public class PersistenceConfig {
    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return new UserIdAuditorAware();
    }
}
