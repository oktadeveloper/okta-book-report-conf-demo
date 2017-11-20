package com.okta.examples.bookreport.service;

import java.util.List;

public interface BookService {

    List<Integer> getMyBookIds(String username);
    void upvote(String username, Integer bookId);
}
