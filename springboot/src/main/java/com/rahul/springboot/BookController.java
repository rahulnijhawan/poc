package com.rahul.springboot;

import com.github.dockerjava.api.exception.NotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    Book addNewBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("{id}")
    Book addNewBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("book not found"));
    }
}
