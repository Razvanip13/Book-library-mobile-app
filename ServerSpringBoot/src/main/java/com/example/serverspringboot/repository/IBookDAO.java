package com.example.serverspringboot.repository;

import com.example.serverspringboot.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface IBookDAO extends JpaRepository<Book, Integer> {

    @Transactional
    void removeBookById(Long id);

    Book getBookById(Long id);

}
