package code.shubham.ratelimiter.service.ratelimiters.implementations;


import client.redis.store.RedisStore;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.ratelimiter.service.ratelimiters.implementations.buckets.TokenBucketJedis;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenBucketJedisRateLimiter extends AbstractRateLimiter<TokenBucketJedis> {
    @Autowired
    private RedisStore redisStore;

    protected TokenBucketJedis newBucket(String key, IRule rule) {
        return new TokenBucketJedis(
                redisStore, key,
                rule.getAllowed(), rule.getTimeDuration(),
                rule.getAllowed(), System.nanoTime());
    }

    @Override
    public AbstractAllowResult allow(TokenBucketJedis bucket) {
        boolean allowed = bucket.allowRequest(1);
        long limit = 0;
        if (allowed)
            limit = (long) Math.floor(bucket.getRemainingTokens());
//        else
//            limit =
        return AbstractAllowResult.builder().
                allowed(allowed).
                limit(limit).
                build();
    }
}
