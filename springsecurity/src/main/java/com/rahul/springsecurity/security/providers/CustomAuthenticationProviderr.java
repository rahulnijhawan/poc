package com.rahul.springsecurity.security.providers;

import com.rahul.springsecurity.security.authentication.CustomAuthenticationn;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CustomAuthenticationProviderr implements AuthenticationProvider {

   private String key = "123456";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // here we implement the authentication logic.
        /**
         * if request is authenticate then return Authentication object
         * else throw exception AuthenticationException
         *
         * if Authentication not supported by this Authentication provider then return null.
         */

        String username = authentication.getName();
        System.out.println("username = " + username);
        if (username.equals(key)) {
            return new CustomAuthenticationn("custom", "custom123", null);


        }
        throw new BadCredentialsException("Authentication failed custom!");


    }

    @Override
    public boolean supports(Class<?> authenticationType /*type of authentication*/) {
        System.out.println("CustomAuthentication authenticationType = " + authenticationType);
        // it calls by AuthenticationManager
        return CustomAuthenticationn.class.equals(authenticationType);
    }
}
