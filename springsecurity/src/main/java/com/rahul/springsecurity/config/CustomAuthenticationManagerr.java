package com.rahul.springsecurity.config;

import com.rahul.springsecurity.security.providers.CustomAuthenticationProviderr;
import com.rahul.springsecurity.security.providers.CustomBasicAuthenticationProviderr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
// move here from securityConfig because it creates circular dependency
public class CustomAuthenticationManagerr {
    @Autowired
    private CustomBasicAuthenticationProviderr authenticationProvider;
    @Autowired
    private CustomAuthenticationProviderr customAuthenticationProvider;

    @Bean
    @Primary
    public AuthenticationManagerBuilder Auth(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider)
                .authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
