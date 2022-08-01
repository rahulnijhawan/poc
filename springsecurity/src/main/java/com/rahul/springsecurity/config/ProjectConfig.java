package com.rahul.springsecurity.config;

import com.rahul.springsecurity.services.MysqlUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableAsync
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ProjectConfig {

//    @Autowired
//    private DataSource dataSource;



    @Bean
    public UserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource());
    }

    //    @Bean
    public UserDetailsService mysqlUserDetailsService() {
        return new MysqlUserDetailService();
    }

    //    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/springsecurity");
        dataSource.setUsername("rahul");
        dataSource.setPassword("rahul");
        return dataSource;
    }

}


