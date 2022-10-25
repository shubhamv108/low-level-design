package code.shubham.commons.filters;

import code.shubham.commons.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
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
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(2)
public class RequestTimestampFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {
        Long now = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setAttribute(Constants.RequestKey.REQUEST_START_TIMESTAMP, now);
        Object requestId = request.getAttribute(Constants.RequestKey.REQUEST_ID);
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
            request.setAttribute(Constants.RequestKey.REQUEST_ID, requestId);
        }
        ThreadContext.put(Constants.RequestKey.REQUEST_ID, (String) requestId);
        chain.doFilter(servletRequest, servletResponse);
        ThreadContext.clearAll();
    }
}
