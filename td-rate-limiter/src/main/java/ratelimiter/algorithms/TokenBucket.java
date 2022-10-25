package ratelimiter.algorithms;

public class TokenBucket {

    private final long maxBucketSize;
    private final long refillRate;

    private double currentBucketSize;
    private long lastRefillTimestamp;

    public TokenBucket(final long maxBucketSize, final long refillRate) {
        this.maxBucketSize = maxBucketSize;
        this.refillRate = refillRate;
        this.currentBucketSize = maxBucketSize;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    // replace syncronization with AtomicReference
    public synchronized boolean allowRequest(int tokens) {
        this.refill();

        if (this.currentBucketSize > tokens) {
            this.currentBucketSize -= tokens;
            return true;
        }

        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        double tokensToAdd = (now - lastRefillTimestamp) * refillRate / 1e9;
        this.currentBucketSize = Math.min(this.currentBucketSize + tokensToAdd, maxBucketSize);
        this.lastRefillTimestamp = now;
    }

}
