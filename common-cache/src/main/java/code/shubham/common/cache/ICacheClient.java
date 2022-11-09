package code.shubham.common.cache;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ICacheClient {

    boolean set(String key, Object value);

    boolean set(String key, Object value, Long timeToLiveInSeconds);

    Optional<Object> get(String key);

    <T> Optional<T> get(String key, Class<T> valueType);

    boolean remove(String key);

    List<Boolean> remove(String... keys);

    boolean setExpiryTimestamp(String key, Date timestamp);

}