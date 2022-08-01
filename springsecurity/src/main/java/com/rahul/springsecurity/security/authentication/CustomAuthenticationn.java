package com.rahul.springsecurity.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationn extends UsernamePasswordAuthenticationToken {
    public CustomAuthenticationn(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomAuthenticationn(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
