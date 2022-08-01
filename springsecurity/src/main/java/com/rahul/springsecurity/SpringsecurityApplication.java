package com.rahul.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class SpringsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityApplication.class, args);
    }


}

class a extends HttpFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        System.out.println("request = " + request);
        doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);

    }
}
