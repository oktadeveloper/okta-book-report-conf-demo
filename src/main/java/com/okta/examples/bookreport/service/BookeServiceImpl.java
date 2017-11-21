package com.okta.examples.bookreport.service;

import com.okta.examples.bookreport.config.BooksConfig;
import com.okta.examples.bookreport.model.Book;
import com.okta.examples.bookreport.repository.BookRepository;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserList;
import com.okta.sdk.resource.user.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookeServiceImpl implements BookService {

    private Client client;

    private BookRepository repository;
    private BooksConfig booksConfig;

    private static final Logger log = LoggerFactory.getLogger(BookeServiceImpl.class);

    @PostConstruct
    public void setup() {
        this.client = Clients.builder().build();
    }

    public BookeServiceImpl(BookRepository repository, BooksConfig booksConfig) {
        this.repository = repository;
        this.booksConfig = booksConfig;
    }

    @Override
    public List<Integer> getMyBookIds(String username) {
        List<Integer> myBookIds = new ArrayList<>();

        // the list of books I added
        List<Book> myBooks = repository.findByOwner(username);
        myBookIds.addAll(myBooks.stream().map(Book::getId).collect(Collectors.toList()));

        // this list of books I upvoted
        List<Integer> upvotes = getUpvotedIds(username);
        myBookIds.addAll(upvotes);
        return myBookIds;
    }

    @Override
    public void upvote(String username, Integer bookId) {
        User user = getUser(username);
        List<Integer> upvotes = getUpvotedIds(username);
        upvotes.add(bookId);
        user.getProfile().put("upvotes", upvotes);
        user.update();
    }

    @Override
    public void reset() {
        // purge existing books
        repository.deleteAll();

        // load books from properties
        repository.save(booksConfig.getBooks());

        // reset all upvotes for all users
        UserList users = client.listUsers();
        users.forEach(user -> {
            user.getProfile().remove("upvotes");
            try {
                user.update();
            } catch (ResourceException e) {
                log.error("Error updating: {}", e.getMessage(), e);
            }
        });
    }

    private User getUser(String username) {
        return client.getUser(username);
    }

    @SuppressWarnings("unchecked")
    private List<Integer> getUpvotedIds(String username) {
        User user = getUser(username);
        UserProfile profile = user.getProfile();
        return profile.get("upvotes") == null ?
            new ArrayList<>() :
            (List<Integer>) profile.get("upvotes");
    }
}
