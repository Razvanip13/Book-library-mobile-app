package com.example.serverspringboot.controller;


import com.example.serverspringboot.domain.Book;
import com.example.serverspringboot.domain.BookProtocol;
import com.example.serverspringboot.repository.IBookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    IBookDAO repo;

    @RequestMapping("/")
    public String getHelloWorld(){
        return "Hello World";
    }


    @RequestMapping(value = {"/books"},method = RequestMethod.GET)
    public Iterable<Book> getBooks() {
        return repo.findAll();
    }

    @RequestMapping(value ={"/books"}, method = RequestMethod.POST)
    public void addBook(@RequestBody Book book){
        repo.save(book);
    }

    @RequestMapping(value = {"/book"}, method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam("id") Long id){

        var request = repo.getBookById(id);
        return new ResponseEntity<Book>(request, HttpStatus.OK);
    }


    @RequestMapping(value ={"/books/package"}, method = RequestMethod.POST)
    public void addBooks(@RequestBody List<Book> books){

        for(var book : books){
            book.setProtocol(BookProtocol.None);
        }
        repo.saveAll(books);
    }

    @RequestMapping(value={"/books"}, method = RequestMethod.DELETE)
    public void deleteBook(@RequestParam("id") Long id){
        repo.removeBookById(id);
    }

    @RequestMapping(value={"/books/delete"}, method = RequestMethod.POST)
    public void deleteBooks(@RequestBody List<Book> books){
        repo.deleteAll(books);
    }

    @RequestMapping(value = "/books", method = RequestMethod.PUT)
    public void updateBook(@RequestBody Book book){
        repo.save(book);
    }

    @RequestMapping(value = "/books/package", method = RequestMethod.PUT)
    public void updateBooks(@RequestBody List<Book> books){
        repo.saveAll(books);
    }

    @RequestMapping(value = {"/books/max"}, method = RequestMethod.GET)
    public ResponseEntity<?> getBookWithBiggestId(){

        var books = repo.findAll();

        var the_book = books.stream().max(Comparator.comparing(Book::getId));

        return new ResponseEntity<Book>(the_book.get(), HttpStatus.OK);
    }

}
