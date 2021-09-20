package ratelimiter.implementations;

import jobs.schedulers.proxies.TokenBucketCacheProxy;
import ratelimiter.IRateLimiter;
import ratelimiter.proxies.ClientIdentifierBuilderProxy;

public class TokenBucketRateLimiter implements IRateLimiter {

    private final ClientIdentifierBuilderProxy clientIdentifierBuilderProxy;
    private final TokenBucketCacheProxy tokenBucketCacheProxy;

    public TokenBucketRateLimiter(final ClientIdentifierBuilderProxy clientIdentifierBuilderProxy,
                                  final TokenBucketCacheProxy tokenBucketCacheProxy) {
        this.clientIdentifierBuilderProxy = clientIdentifierBuilderProxy;
        this.tokenBucketCacheProxy = tokenBucketCacheProxy;
    }

    @Override
    public boolean allowRequest(final String request) {
        return false;
    }

}
