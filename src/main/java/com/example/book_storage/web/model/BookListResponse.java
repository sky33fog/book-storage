package com.example.book_storage.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookListResponse {

    private List<BookResponse> booksResponse = new ArrayList<>();
}