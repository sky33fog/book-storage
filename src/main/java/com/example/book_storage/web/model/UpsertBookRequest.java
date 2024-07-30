package com.example.book_storage.web.model;

import lombok.Data;

@Data
public class UpsertBookRequest {

    private String name;

    private String author;

    private String categoryName;
}