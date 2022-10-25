package code.shubham.commons.filters;

import code.shubham.commons.contexts.TenantContextHolder;
import code.shubham.commons.contexts.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@Order(6)
public class ContextFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userIdString = Optional.ofNullable(request.getHeader("userId"))
                .orElse((String) request.getAttribute("userId"));
        if (userIdString != null)
            UserContextHolder.setUserId(Integer.valueOf(userIdString));

        String tenantId = request.getHeader("tenantId");
        if (tenantId != null)
            TenantContextHolder.setTenant(tenantId);

        chain.doFilter(servletRequest, servletResponse);

        UserContextHolder.clear();
        TenantContextHolder.clear();
    }
}
