package com.test.template;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/*自定义过滤*/
@Configuration
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("***********init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("*********do");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("****************destroy");
    }
}
