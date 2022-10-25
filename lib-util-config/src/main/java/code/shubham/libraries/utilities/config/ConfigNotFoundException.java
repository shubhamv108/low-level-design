package code.shubham.libraries.utilities.config;

public class ConfigNotFoundException extends RuntimeException {
    public ConfigNotFoundException(String configName) {
        super(String.format("No config found with name: %s", configName));
    }
}
