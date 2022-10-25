package code.shubham.common.utils;

import com.google.gson.Gson;

public class JsonUtils {

    private static final Gson GSON = new Gson();

    public static <T> String convert(T object) {
        return GSON.toJson(object);
    }

    public static <T> T convert(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

}
