package code.shubham.authentication.filters;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.service.strategies.JWTAccessTokenStrategy;
import code.shubham.authentication.utils.JWTUtils;
import code.shubham.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Component
//@Order(5)
public class JWTAuthenticationFilter implements Filter {

    private final JWTAccessTokenStrategy accessTokenStrategy;

    @Autowired
    public JWTAuthenticationFilter(final JWTAccessTokenStrategy accessTokenStrategy) {
        this.accessTokenStrategy = accessTokenStrategy;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        this.accessTokenStrategy.validate(
                (HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
