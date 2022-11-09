local last_checked_ts = tonumber(redis.call('get', KEYS[1]));
local tokens_count = tonumber(redis.call('get', KEYS[2]));
local max_hits = tonumber(ARGV[1]);
if last_checked_ts ~= nil then
  local elapsed_ms = tonumber(ARGV[3]) - last_checked_ts;
  local tokens_to_add = math.floor(elapsed_ms * tonumber(ARGV[2]));
  local new_tokens_count = math.min(tokens_count + tokens_to_add - 1, max_hits - 1);
  if new_tokens_count >= 0 then
    redis.call('set', KEYS[2], new_tokens_count);
    redis.call('set', KEYS[1], ARGV[3]);
    return { 1, new_tokens_count };
  end
  return { 0, new_tokens_count };
else
  redis.call('set', KEYS[2], max_hits - 1);
  redis.call('set', KEYS[1], ARGV[3]);
  return { 1, max_hits - 1 };
end