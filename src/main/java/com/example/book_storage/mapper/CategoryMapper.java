package com.example.book_storage.mapper;

import com.example.book_storage.model.Category;
import com.example.book_storage.web.model.CategoryListResponse;
import com.example.book_storage.web.model.CategoryResponse;
import com.example.book_storage.web.model.UpsertCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public Category requestToCategory(UpsertCategoryRequest request) {
        Category category =  new Category();
        category.setName(request.getName());
        return category;
    }

    public CategoryResponse categoryToResponse(Category category) {
        return new CategoryResponse(category.getName());
    }

    public CategoryListResponse categoryListToCategoryResponseList(List<Category> categoryList) {
        CategoryListResponse list = new CategoryListResponse();

        list.setCategoryResponse(categoryList.stream()
                .map(this::categoryToResponse).collect(Collectors.toList()));
        return list;
    }
}