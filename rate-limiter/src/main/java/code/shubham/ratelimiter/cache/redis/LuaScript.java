package code.shubham.ratelimiter.cache.redis;

public interface LuaScript {

    String RELEASE_LOCK_FOR_VALUE_TAKEN_IN_SINGLE_INSTANCE =
            "if redis.call(\"get\",KEYS[1]) == ARGV[1] then " +
                "return redis.call(\"del\",KEYS[1]) " +
            "else " +
                "return 0 " +
            "end ";

    String RATE_LIMIT_PATTERN = "FUNCTION LIMIT_API_CALL(ip) " +
            "ts = CURRENT_UNIX_TIME() " +
            "keyname = ip+\":\"+ts " +
            "MULTI " +
            "    INCR(keyname) " +
            "    EXPIRE(keyname, 10) " +
            "EXEC " +
            "current = RESPONSE_OF_INCR_WITHIN_MULTI " +
            "IF current > 10 THEN " +
            "    ERROR \"too many requests per second\" " +
            "ELSE\n" +
            "    PERFORM_API_CALL()\n" +
            "END";

    String TOKEN_BUCKET_ALLOW =
            "local last_checked_ts = tonumber(redis.call('get', KEYS[1])); " +
            "local tokens_count = tonumber(redis.call('get', KEYS[2])); " +
            "local max_hits = tonumber(ARGV[1]); " +
            "if last_checked_ts ~= nil then " +
            "  local elapsed_ms = tonumber(ARGV[3]) - last_checked_ts; " +
//            "  local tokens_to_add = math.floor((max_hits * elapsed_ms / tonumber(ARGV[2])) + 0.5); " +
            "  local tokens_to_add = math.floor(elapsed_ms * tonumber(ARGV[2])); " +
            "  local new_tokens_count = math.min(tokens_count + tokens_to_add - 1, max_hits - 1); " +
            "  if new_tokens_count >= 0 then " +
            "    redis.call('set', KEYS[2], new_tokens_count); " +
            "    redis.call('set', KEYS[1], ARGV[3]); " +
            "    return { 1, new_tokens_count }; " +
            "  end " +
            "  return { 0, new_tokens_count }; " +
            "else " +
            "  redis.call('set', KEYS[2], max_hits - 1); " +
            "  redis.call('set', KEYS[1], ARGV[3]); " +
            "  return { 1, max_hits - 1 }; " +
            "end";
}