package code.shubham.ratelimit.rules.config;

import code.shubham.common.cache.DefaultDistributedCache;
import code.shubham.common.cache.ICacheClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanRateLimitRulesConfiguration {

    @Bean("DistributedCache")
    public ICacheClient redisCache() {
        return new DefaultDistributedCache();
    }
}
