package com.okta.examples.bookreport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String author;
    String title;
    String url;
    String owner;
    int votes;

    public Book() {}

    public Book(String author, String title, String url, String owner) {
        this.author = author;
        this.title = title;
        this.url = url;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonIgnore
    public String getAuthorLowerCase() {
        if (author != null) {
            return author.toLowerCase();
        }
        return null;
    }

    @JsonIgnore
    public String getTitleLowerCase() {
        if (title != null) {
            return title.toLowerCase();
        }
        return null;
    }

    @Override
    public boolean equals(Object otherBookObj) {
        if (otherBookObj == null || !(otherBookObj instanceof Book)) {
            return false;
        }
        Book otherBook = (Book) otherBookObj;

        // author & title all nulls are equal
        if (this.author == null && otherBook.getAuthor() == null && this.title == null && otherBook.getTitle() == null) {
            return true;
        }

        // author & title matching, case insensitive defines equality
        if (
                this.author != null && this.getAuthorLowerCase().equals(otherBook.getAuthorLowerCase()) &&
                        this.title != null && this.getTitleLowerCase().equals(otherBook.getTitleLowerCase())
                ) {
            return true;
        }
        return false;
    }
}