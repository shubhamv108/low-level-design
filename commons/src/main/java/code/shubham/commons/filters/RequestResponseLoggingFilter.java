package code.shubham.commons.filters;

import code.shubham.commons.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(3)
public class RequestResponseLoggingFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Long requestTimestamp = (Long) request.getAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP);
        Map<String, Object> requestHeaders = this.getHeaders(
                request.getHeaderNames().asIterator(),
                name -> request.getHeader(name));
//        Map<String, String[]> requestParameters = request.getParameterMap();

        String requestBody = ""; // servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("Request:{Timestamp: {}, URI: {} {}, QueryParameters: {}, Headers: {}, Body: {}}",
                requestTimestamp, request.getMethod(), request.getRequestURI(),
                request.getQueryString(), requestHeaders, requestBody);
        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(response);

        chain.doFilter(servletRequest, servletResponse);

        Map<String, Object> responseHeaders = this.getHeaders(
                request.getHeaderNames().asIterator(),
                name -> response.getHeader(name));
        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseBody = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
        responseCacheWrapperObject.copyBodyToResponse();

        Long requestStartTimestamp = (Long) request.getAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP);
        log.info("Response:{StatusCode: {}, Headers: {}, Body: {} StartTime: {}, EndTime: {}}",
                response.getStatus(),
                responseHeaders,
                responseBody,
                requestStartTimestamp,
                System.currentTimeMillis());
    }

    private Map<String, Object> getHeaders(final Iterator<String> headerNames,
                                           final Function<String, Object> headersValue) {
        Map<String, Object> headers = new HashMap<>();
        if (headerNames != null) {
            String headerName;
            while (headerNames.hasNext()) {
                headerName = headerNames.next();
                headers.put(headerName, headersValue.apply(headerName));
            }
        }
        return headers;
    }
}
