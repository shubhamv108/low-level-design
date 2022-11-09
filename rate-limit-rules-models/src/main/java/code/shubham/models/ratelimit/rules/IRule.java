package code.shubham.models.ratelimit.rules;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface IRule {

    String RATE_LIMIT_RULE_KEY = "RateLimitRule::%s::%s::%s::%s";

    Plan getPlan();
    String getApiName();
    String getRole();
    Integer getUserId();
    Integer getAllowed();
    Long getDuration();
    TimeUnit getTimeUnit();

    default Duration getTimeDuration() {
        return Duration.of(this.getDuration(), this.getTimeUnit().toChronoUnit());
    }

    default long getRate(TimeUnit timeUnit) {
        long duration = timeUnit.convert(this.getDuration(), this.getTimeUnit());
        return this.getAllowed() * (duration / this.getDuration());
    }

    default double getRefillRatePerNanoSecond() {
        return ((double) this.getAllowed() / TimeUnit.SECONDS.convert(this.getTimeDuration())) / 1e9;
    }

    default String getKey() {
        return IRule.getKey(getPlan(), getApiName(), getRole(), getUserId());
    }

    static String getKey(Plan plan, String apiName, String role, Integer userId) {
        return String.format(RATE_LIMIT_RULE_KEY, Optional.ofNullable(plan).orElse(Plan.FREE).name(), apiName, role, userId);
    }
}
