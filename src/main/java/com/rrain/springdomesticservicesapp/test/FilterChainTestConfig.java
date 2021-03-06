package com.rrain.springdomesticservicesapp.test;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/* Filter Chain Test Class */
//@Component
public class FilterChainTestConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        filterChain.doFilter(servletRequest, response);
    }


    @Override
    public void destroy() {

    }

}
