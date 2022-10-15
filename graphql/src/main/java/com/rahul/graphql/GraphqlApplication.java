package com.rahul.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

// http://localhost:8080/graphiql?path=/graphql
@SpringBootApplication
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer(CustomerService customerService) {
        return builder -> builder
                .type("Customer", wiring -> wiring
                        .dataFetcher("profile", environment -> customerService.getProfileFor(environment.getSource()))
                )
                .type("Query", wiring -> wiring
                        .dataFetcher("customerById",
                                     environment -> customerService.getCustomerById(Integer.parseInt(environment.getArgument("id"))))
                        .dataFetcher("customers",
                                     environment -> customerService.getCustomers())
                );
    }



}

record Customer(Integer id, String name) {}
record Profile(Integer id, Integer customerId){}
@Service
class CustomerService {

    Profile getProfileFor(Customer customer) {
        return new Profile(customer.id(), customer.id());
    }
    Collection<Customer> getCustomers() {
        return List.of(
                new Customer(1, "rahul"),
                new Customer(2, "nivedita")
        );
    }

    public Customer getCustomerById(Integer id) {
        return getCustomers().stream().filter(customer -> customer.id() == id).findFirst().orElseThrow(() ->new RuntimeException("not found customer"));
    }
}
