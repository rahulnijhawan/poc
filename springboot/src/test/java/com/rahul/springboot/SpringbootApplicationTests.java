package com.rahul.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:tc:mysql:5.7.37:///")
class SpringbootApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void addBookToCatalog() {
        var newBook = new Book(null, "rahul-book");

        webTestClient.post()
                .uri("books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(book.getId()).isNotNull();
                    assertThat(book.getTitle()).isEqualTo(newBook.getTitle());
                });
    }

}
