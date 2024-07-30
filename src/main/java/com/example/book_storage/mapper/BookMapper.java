package com.example.book_storage.mapper;

import com.example.book_storage.model.Book;
import com.example.book_storage.model.Category;
import com.example.book_storage.service.CategoryService;
import com.example.book_storage.web.model.BookListResponse;
import com.example.book_storage.web.model.BookResponse;
import com.example.book_storage.web.model.UpsertBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    @Autowired
    private CategoryService categoryService;

    public Book requestToBook(UpsertBookRequest request) {
        Book book = new Book();

        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        book.setCategory(getCategoryByName(request.getCategoryName()));

        return book;
    }

    public BookResponse bookToResponse(Book book) {
        return new BookResponse(book.getId(), book.getName(), book.getAuthor(), book.getCategory().getName());
    }

    public BookListResponse bookListToBookResponseList(List<Book> bookList) {
        BookListResponse list = new BookListResponse();

        list.setBooksResponse(bookList.stream()
                .map(this::bookToResponse).collect(Collectors.toList()));
        return list;
    }

    private Category getCategoryByName(String categoryName) {
        Category existedCategory = categoryService.findByName(categoryName);

        if(existedCategory == null) {
            existedCategory = categoryService.save(categoryName);
        }
        return existedCategory;
    }
}