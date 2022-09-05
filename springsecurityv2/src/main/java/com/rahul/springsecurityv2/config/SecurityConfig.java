package com.rahul.springsecurityv2.config;

import com.rahul.springsecurityv2.security.CsrfLoggerFilter;
import com.rahul.springsecurityv2.security.CustomCsrfTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CsrfLoggerFilter csrfLoggerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf(httpSecurityCsrfConfigurer -> {
            httpSecurityCsrfConfigurer.ignoringAntMatchers("/csrfdisabled/**");
//            httpSecurityCsrfConfigurer.csrfTokenRepository(new CustomCsrfTokenRepository());
        });

        // use after Csrfilter as it will populate the attribute "_csrf"
        http.addFilterAfter(csrfLoggerFilter, CsrfFilter.class);

//        http.csrf().disable();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
////        httpSecurity.authorizeHttpRequests()
////                .antMatchers("/public").permitAll()
////                .anyRequest().authenticated();
//        return httpSecurity.build();
//    }
}
