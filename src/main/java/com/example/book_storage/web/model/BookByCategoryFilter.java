package com.example.book_storage.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookByCategoryFilter {

    @NotNull(message = "Параметр 'category' должен быть указан.")
    private String category;
}