package code.shubham.ratelimiter.cache.redis;

import client.redis.store.RedisStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class LuaScriptLoader {

    private final RedisStore redisStore;

    @Autowired
    public LuaScriptLoader(final RedisStore redisStore) {
        this.redisStore = redisStore;
    }

    public void load(String luaScriptHashKey, File file) throws IOException {
        String script = new String(new FileInputStream(file).readAllBytes());
        this.load(luaScriptHashKey, script);
    }

    public void load(String luaScriptHashKey, String script) {
        this.redisStore.loadLuaScript(luaScriptHashKey, script);
    }

}
