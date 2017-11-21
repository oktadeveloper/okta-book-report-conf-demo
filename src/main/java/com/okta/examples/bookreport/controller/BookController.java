package com.okta.examples.bookreport.controller;

import com.okta.examples.bookreport.model.Book;
import com.okta.examples.bookreport.repository.BookRepository;
import com.okta.examples.bookreport.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookController {

    @Value("#{ @environment['okta.oauth2.baseUrl'] }")
    private String oktaUrl;

    private BookRepository repository;
    private BookService bookService;

    public BookController(BookRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    @RequestMapping({"/", "/post_logout"})
    public String home(Model model, OAuth2Authentication authentication) {

        if (authentication != null) {
            model.addAttribute("myBookIds", bookService.getMyBookIds(authentication.getName()));
        }

        model.addAttribute("oktaUrl", oktaUrl);
        model.addAttribute("authentication", authentication);
        model.addAttribute("books", repository.findAll());
        model.addAttribute("book", new Book());
        return "home";
    }

    @PreAuthorize("hasAuthority('book_creators')")
    @RequestMapping(value = "/new_book", method = RequestMethod.POST)
    String newBook(@ModelAttribute Book book, Model model, OAuth2Authentication authentication) {
        book.setOwner(authentication.getName());
        book.setVotes(1);
        repository.save(book);
        return home(model, authentication);
    }

    @PreAuthorize("hasAuthority('upvoters')")
    @RequestMapping(value = "/upvote", method = RequestMethod.POST)
    String upvote(@ModelAttribute Book book, Model model, OAuth2Authentication authentication) {
        book = repository.findOne(book.getId());
        book.setVotes(book.getVotes() + 1);
        repository.save(book);

        // add book to list of books you've upvoted
        bookService.upvote(authentication.getName(), book.getId());

        return home(model, authentication);
    }
}