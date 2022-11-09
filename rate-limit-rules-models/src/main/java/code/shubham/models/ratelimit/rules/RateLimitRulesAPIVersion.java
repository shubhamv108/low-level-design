package code.shubham.models.ratelimit.rules;

import java.io.File;

public enum RateLimitRulesAPIVersion {
    V1("v1");

    String version;

    RateLimitRulesAPIVersion(String version) {
        this.version = version;
    }

    public String getURI() {
        return File.separator + this.version;
    }
}
