package code.shubham.ratelimiter.rule;

import code.shubham.models.ratelimit.rules.IRule;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

public class Bucket4jRuleUtil {
    public static Bandwidth getLimit(IRule rule) {
        return Bandwidth.classic(rule.getAllowed(), Refill.intervally(rule.getAllowed(), rule.getTimeDuration()));
    }
}
