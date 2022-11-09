package code.shubham.ratelimiter.service.ratelimiters.implementations.buckets;

import client.redis.store.RedisStore;
import code.shubham.ratelimiter.cache.redis.LuaScript;
import code.shubham.ratelimiter.cache.redis.LuaScriptHashKeys;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TokenBucketJedis extends AbstractTokenBucket {

    private static final String CURRENT_BUCKET_SIZE_KEY = "TokenBucket::CurrentBucketSize::%s";
    private static final String LAST_REFILL_TIMESTAMP_KEY = "TokenBucket::LastRefillTimestamp::%s";

    private String key;

    private RedisStore redisStore;

    public TokenBucketJedis(RedisStore redisStore, String key, long maxBucketSize,
                            Duration refillDuration, double currentBucketSize,
                            long lastRefillTimestamp) {
        super(maxBucketSize, refillDuration, currentBucketSize, lastRefillTimestamp);
        this.key = key;
        this.redisStore = redisStore;
    }

    @Override
    public boolean allowRequest(int tokens) {
        long now = System.nanoTime();
        ArrayList<Long> result = (ArrayList<Long>) this.redisStore.executeLuaScriptForHashKey(
                LuaScriptHashKeys.TOKEN_BUCKET_ALLOW_HASH_KEY,
                List.of(getLastRefillTimestampKey(),
                        getCurrentBucketSizeKey()),
                List.of(String.valueOf(this.maxBucketSize),
                        String.valueOf(this.refillRateInNanoSeconds),
                        String.valueOf(now)),
                LuaScript.TOKEN_BUCKET_ALLOW);
        this.lastRefillTimestamp = now;
        this.currentBucketSize = result.get(1);
        return (result.get(0) == 1L);
    }

    @Override
    protected void refill() {
        throw new UnsupportedOperationException("refill");
    }

    public String getCurrentBucketSizeKey() {
        return String.format(CURRENT_BUCKET_SIZE_KEY, this.key);
    }

    public String getLastRefillTimestampKey() {
        return String.format(LAST_REFILL_TIMESTAMP_KEY, this.key);
    }
}
