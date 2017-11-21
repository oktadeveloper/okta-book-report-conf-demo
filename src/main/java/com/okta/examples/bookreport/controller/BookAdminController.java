package com.okta.examples.bookreport.controller;

import com.okta.examples.bookreport.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BookAdminController {

    private BookService bookService;

    public BookAdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/reset")
    public void reset(HttpServletResponse response) throws IOException {
        bookService.reset();
        response.sendRedirect("/books");
    }
}
