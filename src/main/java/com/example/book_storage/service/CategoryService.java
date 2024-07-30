package com.example.book_storage.service;

import com.example.book_storage.model.Category;
import com.example.book_storage.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category save(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
        return category;
    }
}