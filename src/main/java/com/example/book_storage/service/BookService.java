package com.example.book_storage.service;

import com.example.book_storage.configuration.properties.AppCacheProperties;
import com.example.book_storage.exception.EntityNotFoundException;
import com.example.book_storage.model.Book;
import com.example.book_storage.repository.BookRepository;
import com.example.book_storage.repository.BookSpecification;
import com.example.book_storage.web.model.BookByCategoryFilter;
import com.example.book_storage.web.model.BookByNameAndAuthorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class BookService {


    private final CacheManager redisCacheManager;

    private final BookRepository bookRepository;

    @Cacheable(value = AppCacheProperties.CacheNames.BOOK_BY_NAME_AND_AUTHOR, key = "#filter.name + #filter.author")
    public List<Book> filterByNameAndAuthor(BookByNameAndAuthorFilter filter) {

        return bookRepository.findAll(BookSpecification.withFilterByNameAndAuthor(filter));
    }

    @Cacheable(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY, key = "#filter.category")
    public List<Book> filterByCategory(BookByCategoryFilter filter) {
        return bookRepository.findAll(BookSpecification.withFilterByCategory(filter));
    }

    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY,
                    beforeInvocation = true,
                    key = "#book.category.name"),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_NAME_AND_AUTHOR,
                    beforeInvocation = true,
                    key = "#book.name + #book.author")
    })
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_CATEGORY,
                    beforeInvocation = true,
                    key = "#book.category.name"),
            @CacheEvict(value = AppCacheProperties.CacheNames.BOOK_BY_NAME_AND_AUTHOR,
                    beforeInvocation = true,
                    key = "#book.name + #book.author")
    })
    public Book update(Long id, Book book) {
        Book bookForUpdate = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Книга с id: {0} не найдена", id)));

        redisCacheManager.getCache(AppCacheProperties.CacheNames.BOOK_BY_CATEGORY)
                .evict(bookForUpdate.getCategory().getName());
        redisCacheManager.getCache(AppCacheProperties.CacheNames.BOOK_BY_NAME_AND_AUTHOR)
                .evict(bookForUpdate.getName() + bookForUpdate.getAuthor());

        bookForUpdate.setName(book.getName());
        bookForUpdate.setAuthor(book.getAuthor());
        bookForUpdate.setCategory(book.getCategory());
        return bookRepository.save(bookForUpdate);
    }

    public void deleteById(Long id) {
        Optional<Book> bookForDelete = bookRepository.findById(id);

        if(bookForDelete.isPresent()) {
            redisCacheManager.getCache(AppCacheProperties.CacheNames.BOOK_BY_CATEGORY)
                    .evict(bookForDelete.get().getCategory().getName());
            redisCacheManager.getCache(AppCacheProperties.CacheNames.BOOK_BY_NAME_AND_AUTHOR)
                    .evict(bookForDelete.get().getName() + bookForDelete.get().getAuthor());
        }

        bookRepository.deleteById(id);
    }
}