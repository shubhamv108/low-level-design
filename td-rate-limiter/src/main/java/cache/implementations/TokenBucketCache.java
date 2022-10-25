package cache.implementations;

import cache.IRulesCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketCache implements IRulesCache {

    private final Map cache = new ConcurrentHashMap<>();

    @Override
    public void put() {

    }

    @Override
    public void get() {

    }
}
