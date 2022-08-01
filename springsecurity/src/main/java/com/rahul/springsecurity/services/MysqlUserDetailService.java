package com.rahul.springsecurity.services;

import com.rahul.springsecurity.entities.User;
import com.rahul.springsecurity.repositories.UserRepositoryy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MysqlUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepositoryy userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        User user = userByUsername.orElseThrow(() -> new UsernameNotFoundException("not found: " + username));
        System.out.println("user = " + user);
        return new SecurityMysqlUser(user);
    }
}
