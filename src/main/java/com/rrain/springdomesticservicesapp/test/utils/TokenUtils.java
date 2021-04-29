package com.rrain.springdomesticservicesapp.test.utils;

import org.springframework.security.core.userdetails.UserDetails;

//@Service
public class TokenUtils implements TokenUtilsInterface{

    @Override
    public String getToken(String userDetails) {
        return null;
    }

    @Override
    public String getToken(String userDetails, long expiration) {
        return null;
    }

    @Override
    public boolean validate(String token) {
        return false;
    }

    @Override
    public UserDetails getUserFromToken(String token) {
        return null;
    }
}
