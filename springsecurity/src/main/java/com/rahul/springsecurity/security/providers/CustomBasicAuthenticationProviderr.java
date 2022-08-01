package com.rahul.springsecurity.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class CustomBasicAuthenticationProviderr implements AuthenticationProvider {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // here we implement the authentication logic.
        /*
         * if request is authenticate then return Authentication object
         * else throw exception AuthenticationException
         *
         if Authentication not supported by this Authentication provider then return null.
         */

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        System.out.println("username = " + username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }
        throw new BadCredentialsException("Authentication failed!");


    }

    @Override
    public boolean supports(Class<?> authenticationType /*type of authentication*/) {
        // it calls by AuthenticationManager
        System.out.println("UsernamePasswordAuthenticationToken authenticationType = " + authenticationType);
        return UsernamePasswordAuthenticationToken.class.equals(authenticationType);
    }
}
