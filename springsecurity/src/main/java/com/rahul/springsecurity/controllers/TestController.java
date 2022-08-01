package com.rahul.springsecurity.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class TestController {

//    @Autowired
//    private UserDetailsManager userDetailsManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @GetMapping("test")
//    @Async
    public String test(/*Authentication authentication*/) {
//        System.out.println("authentication = " + authentication);
        //or get authentication by
        //-Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL
        Runnable r = () -> {
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("by SecurityContextHolder authentication1 = " + authentication1);
        };

        // without context pass to thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(r);
        // or executorService.submit(dr);
       // executorService.shutdown();

        // with context pass to thread by runnable decorator

        DelegatingSecurityContextRunnable dr = new DelegatingSecurityContextRunnable(r);
        dr.run();


        //  with context pass to thread by executor decorator
        DelegatingSecurityContextExecutorService delegatingSecurityContextExecutorService = new DelegatingSecurityContextExecutorService(executorService);
        delegatingSecurityContextExecutorService.submit(r);

        return "test ";
    }

    @GetMapping("public")
    public String publicMethod() {
        return "public";
    }

//    @PostMapping("/user")
//    public void createUser(@RequestBody JdbcUserDetails jdbcUserDetails) {
//        jdbcUserDetails.setPassword(passwordEncoder.encode(jdbcUserDetails.getPassword()));
//        userDetailsManager.createUser(jdbcUserDetails);
//    }
}
