package code.shubham.commons.util;

import code.shubham.common.models.ServiceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResponseUtils {

    public static ResponseEntity<?> getOK() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public static ResponseEntity<?> getErrorResponseEntity(int statusCode, Object errors) {
        return getResponseEntity(statusCode, null, errors);
    }

    public static ResponseEntity<?> getResponseEntity(HttpStatus status) {
        return getResponseEntity(status.value(), null, null);
    }

    public static ResponseEntity<?> getResponseEntity(int statusCode) {
        return getResponseEntity(statusCode, null, null);
    }

    public static ResponseEntity<?> getDataResponseEntity(HttpStatus status, Object data) {
        return getResponseEntity(status.value(), data, null);
    }

    public static ResponseEntity<?> getDataResponseEntity(int statusCode, Object data) {
        return getResponseEntity(statusCode, data, null);
    }

    public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors) {
        ServiceResponse response = ServiceResponse.builder().
                statusCode(statusCode).
                data(data).
                error(errors).
                build();
        return ResponseEntity.status(statusCode).body(response);
    }

    public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors, String headerName, String... headerValues) {
        ServiceResponse response = ServiceResponse.builder().
                statusCode(statusCode).
                data(data).
                error(errors).
                build();
        return ResponseEntity.status(statusCode).header(headerName, headerValues).body(response);
    }

    public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Map<String, List<String>> headers) {
        return getResponseEntity(statusCode, data, null, headers);
    }

    public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Map<String, List<String>> headers, String headerKey, String... headerValues) {
        headers.put(headerKey, Arrays.asList(headerValues));
        return getResponseEntity(statusCode, data, null, headers);
    }

    public static ResponseEntity<?> getResponseEntity(int statusCode, Object data, Object errors, Map<String, List<String>> headers) {
        ServiceResponse response = ServiceResponse.builder().
                statusCode(statusCode).
                data(data).
                error(errors).
                build();
        HttpHeaders httpHeaders = null;
        if (headers != null) {
            httpHeaders = new HttpHeaders();
            headers.forEach(httpHeaders::addAll);
        }
        return ResponseEntity.status(statusCode).headers(httpHeaders).body(response);
    }
}
