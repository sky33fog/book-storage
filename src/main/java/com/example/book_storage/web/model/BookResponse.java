package com.example.book_storage.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {

    private Long id;

    private String name;

    private String author;

    private String categoryName;
}