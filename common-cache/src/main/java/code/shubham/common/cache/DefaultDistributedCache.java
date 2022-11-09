package code.shubham.common.cache;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DefaultDistributedCache implements ICacheClient {
    @Override
    public boolean set(String key, Object value) {
        return true;
    }

    @Override
    public boolean set(String key, Object value, Long timeToLiveInSeconds) {
        return true;
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> valueType) {
        return Optional.empty();
    }

    @Override
    public boolean remove(String key) {
        return true;
    }

    @Override
    public List<Boolean> remove(String... keys) {
        return Arrays.asList();
    }

    @Override
    public boolean setExpiryTimestamp(String key, Date timestamp) {
        return true;
    }
}
