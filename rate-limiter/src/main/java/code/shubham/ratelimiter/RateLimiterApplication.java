package code.shubham.ratelimiter;

import client.redis.store.RedisStore;
import code.shubham.commons.annotations.SpringBootJpaApplication;
import code.shubham.ratelimiter.cache.redis.LuaScript;
import code.shubham.ratelimiter.cache.redis.LuaScriptHashKeys;
import code.shubham.ratelimiter.cache.redis.LuaScriptLoader;
import code.shubham.ratelimiter.proxy.RuleProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@SpringBootJpaApplication
public class RateLimiterApplication implements CommandLineRunner {

	@Autowired
	private RuleProxy ruleProxy;

	@Autowired
	private LuaScriptLoader scriptLoader;

	public static void main(String[] args) {
		SpringApplication.run(RateLimiterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			this.ruleProxy.loadCache();
//			File file = ResourceUtils.getFile("classpath:token-bucket.lua");
			this.scriptLoader.load(LuaScriptHashKeys.TOKEN_BUCKET_ALLOW_HASH_KEY, LuaScript.TOKEN_BUCKET_ALLOW);
		} catch (Exception exception) {
			log.error("Exception occurred while Loading rule cache: {}",
					exception.getMessage());
		}
	}
}
