package code.shubham.commonclient;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static String seriealize(Object object)  {
        return GSON.toJson(object);
    }

    public static  <T> T deserialize(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static <T> T convert(Object o, Class<T> clazz) {
        return GSON.fromJson(GSON.toJson(o), clazz);
    }
}
