package com.rahul.springbootjpa.api;

import com.rahul.springbootjpa.jpa.entity.UserEntity;
import com.rahul.springbootjpa.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TestApi {
    @Autowired
    private UserRepository userRepository;

    private ITest iTest;
    @GetMapping
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
