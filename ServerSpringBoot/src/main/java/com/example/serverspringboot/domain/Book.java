package com.example.serverspringboot.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="books")
public class Book implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
    private String description;
    private Long rating ;
    private String image_code;

    @Enumerated(EnumType.STRING)
    private BookProtocol protocol;



    public Book(Long id, String name, String author, BookStatus status, String description, Long rating, String image_code, BookProtocol protocol) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.status = status;
        this.description = description;
        this.rating = rating;
        this.image_code = image_code;
        this.protocol = protocol;
    }

    public Book() {
    }

    public BookProtocol getProtocol() {
        return protocol;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Long getRating() {
        return rating;
    }

    public String getImage_code() {
        return image_code;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public void setImage_code(String image_code) {
        this.image_code = image_code;
    }

    public void setProtocol(BookProtocol protocol) {
        this.protocol = protocol;
    }
}
