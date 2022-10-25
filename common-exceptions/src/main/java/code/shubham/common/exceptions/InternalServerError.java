package code.shubham.common.exceptions;

import java.util.List;
import java.util.Map;

public class InternalServerError extends RuntimeException {
    private Map<String, List<String>> message;
    public InternalServerError() {}
    public InternalServerError(String message) {
        super(message);
    }
    public InternalServerError(Map<String, List<String>> message) {
        this.message = message;
    }
}
