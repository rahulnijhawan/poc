package com.rahul.springsecurity.config;

import com.rahul.springsecurity.security.filters.CustomAuthenticationFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfigg {


//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return null;
//    }



    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.httpBasic();
        http.addFilterAt(customAuthenticationFilter, BasicAuthenticationFilter.class);
        // for client
//        http.oauth2Client(Customizer.withDefaults());
        // for resource server
        http.authorizeHttpRequests()
                .antMatchers("/public").permitAll()
                .anyRequest().authenticated();
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
       // http.csrf().disable();
        return http.build();
    }

    /*@Bean
    public InitializingBean initializingBean() {
        return () -> {
            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }*/

}
