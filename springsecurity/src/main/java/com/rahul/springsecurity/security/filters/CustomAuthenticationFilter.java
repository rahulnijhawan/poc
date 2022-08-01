package com.rahul.springsecurity.security.filters;

import com.rahul.springsecurity.security.authentication.CustomAuthenticationn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter/* implements Filter*/ extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager manager;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");

        var a = new CustomAuthenticationn(authorization, null);

//        try {
            System.out.println("authorization = " + authorization);

            Authentication result = manager.authenticate(a);

            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);
                chain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
       /* } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }*/
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/test");
    }
/*
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;
        String authorization = httpRequest.getHeader("Authorization");
        System.out.println("authorization = " + authorization);
        var  customAuthentication = new CustomAuthentication(authorization, null);


*/
/*
        try {
*//*

            Authentication authenticate = manager.authenticate(customAuthentication);

            // can remove this condition if isAuthenticated() check and throw error by AuthenticationProvider.
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                chain.doFilter(request, response);
            }
       */
/* } catch (AuthenticationException e) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }*//*

    }
*/
}
