//package code.shubham.ratelimiter.bucket4j.configuration;
//
//import io.github.bucket4j.grid.ProxyManager;
//import org.redisson.config.Config;
//import org.redisson.jcache.configuration.RedissonConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.cache.CacheManager;
//import javax.cache.Caching;
//
////@Configuration
//public class RedisConfiguration  {
//
//    @Bean
//    public Config config() {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://localhost:6379");
//        return config;
//    }
//
//    @Bean
//    public CacheManager cacheManager(Config config) {
//        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
//        cacheManager.createCache("cache", RedissonConfiguration.fromConfig(config));
//        return cacheManager;
//    }
//
////    @Bean
////    ProxyManager<String> proxyManager(CacheManager cacheManager) {
////        return new JCacheProxyManager<>(cacheManager.getCache("cache"));
////    }
//}