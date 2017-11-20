package com.okta.examples.bookreport.repository;

import com.okta.examples.bookreport.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findByAuthor(String author);

    List<Book> findByOwner(String owner);
}
