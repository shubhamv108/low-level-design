package code.shubham.ratelimiter.service.ratelimiters.implementations;

import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.ratelimiter.rule.Bucket4jRuleUtil;
import code.shubham.ratelimiter.service.ratelimiters.RateLimiter;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketLocalBucket4jRateLimiter implements RateLimiter {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

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

    private Bucket newBucket(String key, IRule rule) {
        return Bucket4j.builder()
                .addLimit(Bucket4jRuleUtil.getLimit(rule))
                .build();
    }

    public boolean allow(String key, IRule rule, HttpServletResponse response) {
        Bucket bucket = this.resolveBucket(key, rule);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
            return true;
        }

        long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
        response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
        return false;
    }

}
