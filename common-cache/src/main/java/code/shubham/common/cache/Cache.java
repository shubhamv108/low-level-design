package code.shubham.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class Cache implements ICacheClient {

    private final ICacheClient inMemoryCache;

    private final ICacheClient distributedCache;

    @Autowired
    public Cache(@Qualifier("InMemoryCache") ICacheClient inMemoryCache,
                 @Qualifier("DistributedCache") ICacheClient distributedCache) {
        this.inMemoryCache = inMemoryCache;
        this.distributedCache = distributedCache;
    }


    @Override
    public boolean set(String key, Object value) {
        if (!this.distributedCache.set(key, value))
            return false;
        return this.inMemoryCache.set(key, value);
    }

    @Override
    public boolean set(String key, Object value, Long timeToLiveInMilliSeconds) {
        if (!this.distributedCache.set(key, value, timeToLiveInMilliSeconds))
            return false;
        return this.inMemoryCache.set(key, value, timeToLiveInMilliSeconds);
    }

    @Override
    public Optional<Object> get(String key) {
        Optional<Object> value = this.inMemoryCache.get(key);
        if (value.isPresent())
            return value;

        if (this.distributedCache == null)
            return Optional.empty();

        value = this.distributedCache.get(key);
        if (value.isPresent())
            return value;

        return Optional.empty();
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> valueType) {
        Optional<T> value = this.inMemoryCache.get(key, valueType);
        if (value.isPresent())
            return value;

        if (this.distributedCache == null)
            return Optional.empty();

        value = this.distributedCache.get(key, valueType);
        if (value.isPresent())
            return value;

        return Optional.empty();
    }

    @Override
    public boolean remove(String key) {
        return this.inMemoryCache.remove(key) &&
                this.distributedCache != null &&
                this.distributedCache.remove(key);
    }

    @Override
    public List<Boolean> remove(String... keys) {
        List<Boolean> result = this.inMemoryCache.remove(keys);
        if (distributedCache == null)
            return result;
        return this.distributedCache.remove(keys);
    }

    @Override
    public boolean setExpiryTimestamp(String key, Date timestamp) {
        boolean result = this.inMemoryCache.setExpiryTimestamp(key, timestamp);
        if (this.distributedCache == null)
            return result;
        return this.distributedCache.setExpiryTimestamp(key, timestamp);
    }
}
