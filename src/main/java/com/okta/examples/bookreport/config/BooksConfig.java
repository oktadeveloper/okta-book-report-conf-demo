package com.okta.examples.bookreport.config;

import com.okta.examples.bookreport.model.Book;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:books-config.properties")
@ConfigurationProperties("books-config")
public class BooksConfig {
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
