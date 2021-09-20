package ratelimiter;

public interface IRateLimiter {

    boolean allowRequest(String request);

}
