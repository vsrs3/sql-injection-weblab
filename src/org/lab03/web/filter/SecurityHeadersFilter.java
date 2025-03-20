package org.lab03.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "securityHeadersFilter", urlPatterns = { "/*" })
public class SecurityHeadersFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Set security header
        httpResponse.setHeader("Content-Security-Policy", "script-src 'self';");
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
        
        // Continue filter chain
        chain.doFilter(request, httpResponse);
    }

    @Override
    public void destroy() {
       
    }
}