package code.shubham.ratelimiter.configuration;

import client.redis.store.RedisStore;
import client.redis.store.impl.JedisStore;
import code.shubham.common.cache.DefaultDistributedCache;
import code.shubham.common.cache.ICacheClient;
import code.shubham.ratelimiter.service.ratelimiters.RateLimiter;
import code.shubham.ratelimiter.service.ratelimiters.implementations.TokenBucketJedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigurationRateLimiter {

    @Bean
    public RateLimiter rateLimiter() {
        return new TokenBucketJedisRateLimiter();
    }

    @Bean("DistributedCache")
    public ICacheClient cacheClient() {
        return new DefaultDistributedCache();
    }

    @Bean("RedisStore")
    public RedisStore redisStore() {
        return new JedisStore();
    }
}
