package ratelimiter.algorithms;

public class LeakyBucket {

    private final double ratePerNanoSeconds;
    private final double burst;

    private long lastRefreshTimestamp;
    private double waterInBucket;

    public LeakyBucket(final double ratePerNanoSeconds, final double burst) {
        this.ratePerNanoSeconds = ratePerNanoSeconds;
        this.burst = burst;
    }

    public synchronized boolean allowRequest(final double water) {
        this.refreshWater();
        if ((this.waterInBucket + water) < this.burst) {
            this.waterInBucket += water;
            return true;
        }
        return false;
    }

    private void refreshWater() {
        long now = System.nanoTime();
        this.waterInBucket = Math.max(0, waterInBucket - ((now-lastRefreshTimestamp) * ratePerNanoSeconds));
        this.lastRefreshTimestamp = now;
    }
}
