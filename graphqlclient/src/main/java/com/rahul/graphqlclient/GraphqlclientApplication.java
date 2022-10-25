package com.rahul.graphqlclient;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.RSocketGraphQlClient;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class GraphqlclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlclientApplication.class, args);
    }

    @Bean
    HttpGraphQlClient httpGraphQlClient() {
        return HttpGraphQlClient.builder()
                .url("http://127.0.0.1:9081/graphql")
                .build();
    }
    @Bean
    RSocketGraphQlClient rSocketGraphQlClient(RSocketGraphQlClient.Builder<?> builder) {
        return builder.tcp("127.0.0.1", 9083)
                .route("graphql")
                .build();

    }

    @Bean
    ApplicationRunner applicationRunner(RSocketGraphQlClient rSocketGraphQlClient , HttpGraphQlClient httpGraphQlClient) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                var httpRequestDocument= """
                        query{
                            customerById(id: 2) {
                                 id, name
                            }
                        }
                        """;
                httpGraphQlClient.document(httpRequestDocument)
                        .retrieve("customerById")
                        .toEntity(Customer.class)
                        .subscribe(System.out::println);

                var rsocketRequestDocument = """
                        subscription {
                            greetings{greeting}
                        }
                        """;
                rSocketGraphQlClient.document(rsocketRequestDocument)
                        .retrieveSubscription("greetings")
                        .toEntity(Greeting.class)
                        .subscribe(System.out::println);
            }
        };
    }
}

record Greeting(String greeting){}

record Customer(Integer id, String name){}
