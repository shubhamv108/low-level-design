package code.shubham.ratelimiter.service.ratelimiters.implementations;


import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.ratelimiter.service.ratelimiters.implementations.buckets.TokenBucketLocal;

public class TokenBucketLocalRateLimiter extends AbstractRateLimiter<TokenBucketLocal> {
    protected TokenBucketLocal newBucket(String key, IRule rule) {
        return new TokenBucketLocal(rule.getAllowed(), rule.getTimeDuration(), rule.getAllowed(), System.nanoTime());
    }

    @Override
    public AbstractRateLimiter.AbstractAllowResult allow(TokenBucketLocal bucket) {
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
