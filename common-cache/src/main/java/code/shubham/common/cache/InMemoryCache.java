package code.shubham.common.cache;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("InMemoryCache")
public class InMemoryCache implements ICacheClient {

    private final HashMap<String, CacheEntry> cache = new HashMap<>();

    private static final Long DEFAULT_TTL_IN_MILLISECONDS = 86400L * 100;

    @Override
    public boolean set(String key, Object value) {
        return this.set(key, value, DEFAULT_TTL_IN_MILLISECONDS);
    }

    @Override
    public boolean set(String key, Object value, Long timeToLiveInMilliseconds) {
        this.cache.put(key, CacheEntry.builder().
                expiryAt(System.currentTimeMillis() + timeToLiveInMilliseconds).
//                clazz(value.getClass()).
                value(value).
                build());
        return true;
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(this.cache.get(key)).map(entry -> entry.getValue());
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> valueType) {
        return Optional.ofNullable(this.cache.get(key)).
                map(entry -> entry.getValue()).
                map(valueType::cast);
    }

    @Override
    public boolean remove(String key) {
        this.cache.remove(key);
        return true;
    }

    @Override
    public List<Boolean> remove(String... keys) {
        return Arrays.stream(keys).map(this::remove).collect(Collectors.toList());
    }

    @Override
    public boolean setExpiryTimestamp(String key, Date expiryTimestamp) {
        return Optional.ofNullable(this.cache.get(key)).
                map(entry -> {
                    entry.setExpiryAt(expiryTimestamp.getTime());
                    return true;
                }).orElse(false);
    }
}
