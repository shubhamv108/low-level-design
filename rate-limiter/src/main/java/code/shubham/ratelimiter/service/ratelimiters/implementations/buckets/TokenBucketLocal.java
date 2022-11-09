package code.shubham.ratelimiter.service.ratelimiters.implementations.buckets;

import java.time.Duration;

public class TokenBucketLocal extends AbstractTokenBucket {

    public TokenBucketLocal(
            long maxBucketSize,
            Duration refillDuration,
            double currentBucketSize,
            long lastRefillTimestamp) {
        super(maxBucketSize, refillDuration, currentBucketSize, lastRefillTimestamp);
    }


    @Override
    public synchronized boolean allowRequest(int tokens) {
        this.refill();

        if (this.currentBucketSize > tokens) {
            this.currentBucketSize -= tokens;
            return true;
        }

        return false;
    }

    @Override
    protected void refill() {
        long now = System.nanoTime();
        double tokensToAdd = (now - this.getLastRefillTimestamp()) * this.refillRateInNanoSeconds;
        this.currentBucketSize = Math.min(this.currentBucketSize + tokensToAdd, this.maxBucketSize);
        this.lastRefillTimestamp = now;
    }
}
