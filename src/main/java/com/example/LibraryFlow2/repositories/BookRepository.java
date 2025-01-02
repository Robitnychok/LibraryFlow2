package com.example.LibraryFlow2.repositories;

import com.example.LibraryFlow2.models.Book;
import com.example.LibraryFlow2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findById(long id);
    void deleteById(long id);

    List<Book> findByTitleStartingWith(String titleStartingWith);
}
