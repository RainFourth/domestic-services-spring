package com.rrain.springdomesticservicesapp.test.config;

import com.rrain.springdomesticservicesapp.test.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

//@Component
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {



    //@Autowired private MongoUserDetailsService userService;
    @Autowired private TokenUtils tokenUtils;
    @Autowired private AuthenticationManager authManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Map<String, String[]> params = request.getParameterMap();
        if (params.containsKey("token")) {
            String token = params.get("token")[0]; // grab the first "token" parameter

            // validate the token
            if (tokenUtils.validate(token)){
                // determine the user based on the (already validated) token
                UserDetails userDetails = tokenUtils.getUserFromToken(token);
                // build an Authentication object with the user's info
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                // set the authentication into the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
            }
        }
        // continue thru the filter chain
        filterChain.doFilter(request, response);
    }
}
