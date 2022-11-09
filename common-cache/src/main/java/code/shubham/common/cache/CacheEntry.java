package code.shubham.common.cache;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CacheEntry {
    private Object value;
    private long expiryAt;
//    private Class<?> clazz;

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryAt;
    }

    public Object getValue() {
        if (this.isExpired())
            return null;
        return value;
    }

    public void setExpiryAt(long expiryAt) {
        this.expiryAt = expiryAt;
    }
}
