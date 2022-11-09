package code.shubham.ratelimiter.service.ratelimiters;

import code.shubham.models.ratelimit.rules.IRule;

import javax.servlet.http.HttpServletResponse;

public interface RateLimiter {
    boolean allow(String key, IRule rule, HttpServletResponse response);
}
