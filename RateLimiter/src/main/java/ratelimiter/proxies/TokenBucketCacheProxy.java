package ratelimiter.proxies;

import ratelimiter.components.TokenBucketCacheClient;

public class TokenBucketCacheProxy {

    private final TokenBucketCacheClient tokenBucketCacheClient;

    public TokenBucketCacheProxy(final TokenBucketCacheClient tokenBucketCacheClient) {
        this.tokenBucketCacheClient = tokenBucketCacheClient;
    }
}
