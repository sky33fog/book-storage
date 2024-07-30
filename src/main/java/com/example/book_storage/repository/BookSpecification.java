package com.example.book_storage.repository;

import com.example.book_storage.model.Book;
import com.example.book_storage.web.model.BookByCategoryFilter;
import com.example.book_storage.web.model.BookByNameAndAuthorFilter;
import org.springframework.data.jpa.domain.Specification;

public interface BookSpecification {

    static Specification<Book> withFilterByNameAndAuthor(BookByNameAndAuthorFilter filter) {
            return Specification.where(byName(filter.getName()))
                    .and(byAuthor(filter.getAuthor()));
    }

    static Specification<Book> withFilterByCategory(BookByCategoryFilter filter) {
        return Specification.where(byCategoryName(filter.getCategory()));
    }

    static Specification<Book> byName(String bookName) {
        return (root, query, criteriaBuilder) -> {
            if(bookName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("name"), bookName);
        };
    }

    static Specification<Book> byAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if(author == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("author"), author);
        };
    }

    static Specification<Book> byCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if(categoryName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("name"), categoryName);
        };
    }
}