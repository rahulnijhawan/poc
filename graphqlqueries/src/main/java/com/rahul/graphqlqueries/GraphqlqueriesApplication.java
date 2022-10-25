package com.rahul.graphqlqueries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class GraphqlqueriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlqueriesApplication.class, args);
    }

}

@Controller
class GreetingsController {

    ConcurrentHashMap<Integer, Customer> db = new ConcurrentHashMap<>();
    AtomicInteger id = new AtomicInteger();

    @MutationMapping
    Customer addCustomer(@Argument String name) {
        int id = this.id.incrementAndGet();
        Customer value = new Customer(id, name);
        db.put(id, value);
        return value;
    }

    @QueryMapping
    Customer customerById(@Argument Integer id) {
        return db.get(id);
    }

    @QueryMapping
    Flux<Customer> customers(@Argument Integer id) {
        return Flux.fromIterable(db.values());
    }

    @BatchMapping
    Map<Customer, Account> account(List<Customer> customers) {
//        if (true) throw new RuntimeException("heee");
        System.out.println("calling account for customers.size() = " + customers.size());
        return customers.stream()
                .collect(Collectors.toMap(customer -> customer, customer -> new Account(customer.id())));
    }

//

    @QueryMapping
    String helloWithName(@Argument String name) {
        return "hello " + name;
    }

    //@QueryMapping short form of SchemaMapping
    @SchemaMapping(typeName = "Query", field = "hello")
    String hello() {
        return "hello query";
    }
}

record Account(Integer id) {
}

record Customer(Integer id, String name) {
}
