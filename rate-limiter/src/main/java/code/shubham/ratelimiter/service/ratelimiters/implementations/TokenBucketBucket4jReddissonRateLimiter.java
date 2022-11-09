package code.shubham.ratelimiter.service.ratelimiters.implementations;

import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.ratelimiter.rule.Bucket4jRuleUtil;
import code.shubham.ratelimiter.service.ratelimiters.RateLimiter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.grid.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.function.Supplier;

public class TokenBucketBucket4jReddissonRateLimiter implements RateLimiter {

    private final ProxyManager proxyManager;

    @Autowired
    public TokenBucketBucket4jReddissonRateLimiter(final ProxyManager proxyManager) {
        this.proxyManager = proxyManager;
    }

    @Override
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

    public Bucket resolveBucket(String key, IRule rule) {
        Supplier<BucketConfiguration> configSupplier = this.getConfigSupplierForUser(rule);
        return this.proxyManager.getProxy(key, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(IRule rule) {
        Bandwidth limit = Bucket4jRuleUtil.getLimit(rule);
        return () -> new BucketConfiguration(Arrays.asList(limit));
    }
}
