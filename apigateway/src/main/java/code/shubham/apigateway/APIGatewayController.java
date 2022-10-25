package code.shubham.apigateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

@RestController
public class APIGatewayController {

    @RequestMapping(value = "{.*}", method = {
            RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST })
    public ResponseEntity<?> invoke(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        String requestUrl = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        String queryString = request.getQueryString();
        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
            }
        }
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return null;
    }

}
