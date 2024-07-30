package com.example.book_storage.web.controller;

import com.example.book_storage.mapper.BookMapper;
import com.example.book_storage.model.Book;
import com.example.book_storage.service.BookService;
import com.example.book_storage.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookMapper mapper;

    @GetMapping("/by_name_and_author")
    public ResponseEntity<BookListResponse> filterBy(@Valid BookByNameAndAuthorFilter filter) {
        return ResponseEntity.ok(mapper.bookListToBookResponseList(bookService.filterByNameAndAuthor(filter)));
    }

    @GetMapping("/by_category")
    public ResponseEntity<BookListResponse> filterBy(@Valid BookByCategoryFilter filter) {
        return ResponseEntity.ok(mapper.bookListToBookResponseList(bookService.filterByCategory(filter)));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody UpsertBookRequest request) {
        Book newBook = bookService.save(mapper.requestToBook(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.bookToResponse(newBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @RequestBody UpsertBookRequest request) {
       return ResponseEntity.ok(mapper.bookToResponse(bookService.update(id, mapper.requestToBook(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}