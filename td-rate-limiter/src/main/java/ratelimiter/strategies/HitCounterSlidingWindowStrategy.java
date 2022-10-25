package ratelimiter.strategies;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class HitCounterSlidingWindowStrategy {

    private final long requestLimit;
    private final long interval;

    private final Map<String, HitCounter> clientTimestamps = new ConcurrentHashMap<>();
    private final ClientLock clientLock = new ClientLock();
    private final Thread cleaner;

    public HitCounterSlidingWindowStrategy(final long requestLimit, final long interval, final long cleanupDelay) {
        this.requestLimit = requestLimit;
        this.interval = interval;
        this.cleaner = new Thread(this.new Cleaner(cleanupDelay));
        this.cleaner.start();
    }

    class HitCounter {
        public Deque<Long> timestamps = new LinkedList<>();
        public boolean hit(final long timestamp) {
            while (!this.timestamps.isEmpty() && timestamp - this.timestamps.peek() >= interval) this.timestamps.poll();
            if (this.timestamps.size() < requestLimit) {
                this.timestamps.offer(timestamp);
                return true;
            }
            return false;
        }
        public Long getLastTimestamp() {
            return this.timestamps.getLast();
        }
    }

    public boolean allowRequest(final String clientId) {
        ReentrantLock lock = null;
        try {
            (lock = this.clientLock.get(clientId)).lock();
            HitCounter hitCounter = this.clientTimestamps.get(clientId);
            if (hitCounter == null) {
                this.clientTimestamps.put(clientId, new HitCounter());
            }
            return hitCounter.hit(System.nanoTime());
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    private class ClientLock {
        private final Map<String, ReentrantLock> clientLocks = new ConcurrentHashMap<>();
        private ReentrantLock get(final String clientId) {
            var lock = this.clientLocks.get(clientId);
            if (lock == null) {
                synchronized (clientId) {
                    lock = this.clientLocks.get(clientId);
                    if (lock == null) {
                        this.clientLocks.put(clientId, lock = new ReentrantLock());
                    }
                }
            }
            return lock;
        }
        private ReentrantLock remove(final String clientId) {
            return this.clientLocks.remove(clientId);
        }
    }

    private class Cleaner implements Runnable {
        private final long cleanupTimeout;
        private Cleaner(final long cleanupTimeout) {
            this.cleanupTimeout = cleanupTimeout;
        }

        @Override
        public void run() {
            while (true) {
                while (!clientTimestamps.isEmpty()) {
                    for (Map.Entry<String, HitCounter> entry : clientTimestamps.entrySet()) {
                        ReentrantLock lock = null;
                        try {
                            (lock = clientLock.get(entry.getKey())).lock();
                            Long lastTimestamp = entry.getValue().getLastTimestamp();
                            if (lastTimestamp != null && System.nanoTime() - lastTimestamp > cleanupTimeout) {
                                clientTimestamps.remove(entry.getKey());
                                lock = clientLock.remove(entry.getKey());
                            }
                        } finally {
                            if (lock != null) {
                                lock.unlock();
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
