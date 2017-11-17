package com.okta.examples.bookreport.controller;

import com.okta.examples.bookreport.model.Book;
import com.okta.examples.bookreport.repository.BookRepository;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @RequestMapping({"/", "/post_logout"})
    public String home(Model model, OAuth2Authentication authentication) {

        List<Book> myBooks = new ArrayList<>();
        if (authentication != null) {
            myBooks = repository.findByOwner(authentication.getName());
        }

        model.addAttribute("authentication", authentication);
        model.addAttribute("books", repository.findAll());
        model.addAttribute("myBooks", myBooks);
        model.addAttribute("book", new Book());
        return "home";
    }

    @RequestMapping(value = "/new_book", method = RequestMethod.POST)
    String newBook(@ModelAttribute Book book, Model model, OAuth2Authentication authentication) {
        book.setOwner(authentication.getName());
        book.setVotes(1);
        repository.save(book);
        return home(model, authentication);
    }

    @RequestMapping(value = "/upvote", method = RequestMethod.POST)
    String upvote(@ModelAttribute Book book, Model model, OAuth2Authentication authentication) {
        book = repository.findOne(book.getId());
        book.setVotes(book.getVotes() + 1);
        repository.save(book);
        return home(model, authentication);
    }
}