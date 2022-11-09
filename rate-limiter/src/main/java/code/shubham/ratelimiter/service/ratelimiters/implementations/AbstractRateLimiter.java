package code.shubham.ratelimiter.service.ratelimiters.implementations;

import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.ratelimiter.service.ratelimiters.RateLimiter;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRateLimiter<Bucket> implements RateLimiter {

    @Builder
    @Getter
    public static class AbstractAllowResult {
        private long limit;
        private boolean allowed;
    }

    private final Map<String, Bucket> buckets = new HashMap<>();

    @Override
    public boolean allow(String key, IRule rule, HttpServletResponse response) {
        Bucket bucket = this.resolveBucket(key, rule);
        AbstractAllowResult allowResult = allow(bucket);
        if (allowResult.isAllowed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(allowResult.getLimit()));
            return true;
        }

        long waitForRefill = allowResult.getLimit() / 1_000_000_000;
        response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
        return false;
    }

    private Bucket resolveBucket(String key, IRule rule) {
        Bucket bucket = this.buckets.get(key);
        if (bucket == null)
//            synchronized (key) {
//                bucket = this.buckets.get(key);
//                if (bucket == null)
            buckets.put(key, bucket = this.newBucket(key, rule));
//            }
        return bucket;
    }

    abstract Bucket newBucket(String key, IRule rule);

    abstract AbstractAllowResult allow(Bucket bucket);
}
