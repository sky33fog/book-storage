package com.example.book_storage.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookByNameAndAuthorFilter {

    @NotNull(message = "Параметр 'name' должен быть указан.")
    private String name;

    @NotNull(message = "Параметр 'author' должен быть указан.")
    private String author;
}