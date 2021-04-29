package com.rrain.springdomesticservicesapp.test.utils;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenUtilsInterface {

    String getToken(String userDetails);
    String getToken(String userDetails, long expiration);
    boolean validate(String token);
    UserDetails getUserFromToken(String token);

}
