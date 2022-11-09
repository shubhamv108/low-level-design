package code.shubham.library.ratelimit.cache;

import code.shubham.common.cache.Cache;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.models.ratelimit.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class RuleCache {

    private final Cache cache;

    @Autowired
    public RuleCache(Cache cache) {
        this.cache = cache;
    }

    public boolean put(IRule[] rules) {
        return this.put(Arrays.stream(rules));
    }

    public boolean put(List<? extends IRule> rules) {
        return this.put(rules.stream());
    }

    public boolean put(Stream<? extends IRule> rules) {
        rules.forEach(this::set);
        return true;
    }

    public boolean set(IRule rule) {
        return this.cache.set(rule.getKey(), rule);
    }

    public Optional<IRule> get(Plan plan, String apiName, String role, Integer userId) {
        String key = IRule.getKey(plan, apiName, role, userId);
        return this.get(key);
    }

    public Optional<IRule> get(String key) {
        return this.cache.get(key, IRule.class);
    }
}
