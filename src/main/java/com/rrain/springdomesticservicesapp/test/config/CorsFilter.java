package com.rrain.springdomesticservicesapp.test.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getHeader("referer"));
        var origin = request.getHeader("referer");
        origin = origin.substring(0, origin.length()-1);
        response.addHeader("Access-Control-Allow-Origin", origin);
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        //response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        //response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addIntHeader("Access-Control-Max-Age", 3600);
        filterChain.doFilter(request, response);
    }
}
