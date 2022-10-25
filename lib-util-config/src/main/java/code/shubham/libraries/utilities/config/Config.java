package code.shubham.libraries.utilities.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class Config {

    public static void load(String fileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
            System.setProperties(props);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean has(String configName) {
        return System.getProperties().containsKey(configName) || System.getenv().containsKey(configName);
    }

    public static int getInt(String configName) {
        return Integer.valueOf(Config.get(configName));
    }

    public static double getDouble(String configName) {
        return Double.valueOf(Config.get(configName));
    }

    public static float getFloat(String configName) {
        return Float.valueOf(Config.get(configName));
    }

    public static BigInteger getBigInt(String configName) {
        return new BigInteger(Config.get(configName));
    }

    public static BigDecimal getBigDecimal(String configName) {
        return new BigDecimal(Config.get(configName));
    }

    public static String[] getArray(String configName) {
        return Config.get(configName).split(",");
    }

    public static List<String> getList(String configName) {
        return Arrays.stream(Config.get(configName).split(",")).
                collect(Collectors.toList());
    }

    public static String get(String configName) {
        Object value = System.getProperties().get(configName);
        if (value == null)
            return (Optional.ofNullable(System.getenv().get(configName)).
                    orElseThrow(() -> new ConfigNotFoundException(configName)));
        return String.valueOf(value);
    }

}
