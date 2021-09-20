package jobs.schedulers.implementations;

import jobs.schedulers.proxies.RulesServiceProxy;
import jobs.schedulers.proxies.TokenBucketCacheProxy;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RetrieveJobScheduler {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private final RulesServiceProxy rulesServiceProxy;
    private final TokenBucketCacheProxy tokenBucketCacheProxy;

    public RetrieveJobScheduler(final RulesServiceProxy rulesServiceProxy, final TokenBucketCacheProxy tokenBucketCacheProxy) {
        this.rulesServiceProxy = rulesServiceProxy;
        this.tokenBucketCacheProxy = tokenBucketCacheProxy;
    }

    public ScheduledFuture start(final Runnable runnable) {
        return this.executor.schedule(runnable, 10, TimeUnit.MINUTES);
    }

    public void stop() throws InterruptedException {
        this.executor.shutdown();
        while (!this.executor.isTerminated()) {
            Thread.sleep(10000);
        }
    }

}
