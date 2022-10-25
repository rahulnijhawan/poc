package com.example.grahphqlsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@EnableReactiveMethodSecurity
@SpringBootApplication
public class GrahphqlSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrahphqlSecurityApplication.class, args);
    }

   /* @Bean
    SecurityWebFilterChain securityWebFilterChain() {
        return new SecurityWebFilterChain() {
            @Override
            public Mono<Boolean> matches(ServerWebExchange exchange) {
                return Mono.just(false);
            }

            @Override
            public Flux<WebFilter> getWebFilters() {
                return Flux.empty()  ;
            }
        };
    }*/

    @Bean
    MapReactiveUserDetailsService authentication() {
        var users = Map.of("rahul", new String[]{"USER"},
                           "navi", "ADMIN, USER".split(","))
                .entrySet()
                .stream()
                .map(u -> User.withDefaultPasswordEncoder()
                        .username(u.getKey())
                             .password("pass")
                             .roles(u.getValue())
                             .build()
                        )
                .toList();

        return new MapReactiveUserDetailsService(users);
    }

    @Bean
    SecurityWebFilterChain authorize(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ae -> ae.anyExchange().permitAll())
                .httpBasic(Customizer.withDefaults())
                .build();

    }

}

record Customer(Integer id, String name) {

}

@Controller
class SecureGraphqlController {


    private final CRMService crmService;

    SecureGraphqlController(CRMService crmService) {
        this.crmService = crmService;
    }

    @QueryMapping
    Mono<Customer> customerById(@Argument Integer id) {
       return crmService.getCustomerById(id);
    }

    @MutationMapping
    Mono<Customer> insert(@Argument String name) {
        return crmService.insert(name);
    }
}

@Service
class CRMService {

    private final Map<Integer, Customer> db = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger();

    @Secured("ROLE_USER")
    Mono<Customer> getCustomerById(Integer id) {
        return Mono.just(db.get(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    Mono<Customer> insert(String name) {
        var newCustomer = new Customer(id.incrementAndGet(), name);
        this.db.put(newCustomer.id(), newCustomer);
         return Mono.just(newCustomer);

    }
}
