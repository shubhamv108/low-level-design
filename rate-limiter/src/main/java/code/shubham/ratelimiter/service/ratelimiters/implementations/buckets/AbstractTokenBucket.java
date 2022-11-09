package code.shubham.ratelimiter.service.ratelimiters.implementations.buckets;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTokenBucket {
    protected final long maxBucketSize;
    protected final double refillRateInNanoSeconds;

    protected double currentBucketSize;
    protected long lastRefillTimestamp;

    protected AbstractTokenBucket(
            long maxBucketSize,
            Duration refillDuration,
            double currentBucketSize,
            long lastRefillTimestamp) {
        this.maxBucketSize = maxBucketSize;
        this.refillRateInNanoSeconds = this.getRefillRatePerNanoSecond(maxBucketSize, refillDuration);
        this.currentBucketSize = currentBucketSize;
        this.lastRefillTimestamp = lastRefillTimestamp;
    }

    private double getRefillRatePerNanoSecond(long maxBucketSize, Duration refillDuration) {
        return ((double) maxBucketSize / TimeUnit.SECONDS.convert(refillDuration)) / 1e9;
    }

    public abstract boolean allowRequest(int tokens);
    protected abstract void refill();

    protected long getLastRefillTimestamp() {
        return this.lastRefillTimestamp;
    }

    public double getRemainingTokens() {
        return this.currentBucketSize;
    }
}
