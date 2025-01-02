package com.example.LibraryFlow2.services;

import com.example.LibraryFlow2.models.Book;
import com.example.LibraryFlow2.models.Person;
import com.example.LibraryFlow2.repositories.BookRepository;
import com.example.LibraryFlow2.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> getAllBooks(int page, int booksPerPage){
        Pageable pageable = PageRequest.of(page, booksPerPage);
        return bookRepository.findAll(pageable);
    }

    public List<Book> getAllBooks(Boolean sortByYear){
        return bookRepository.findAll(Sort.by("publicationYear"));
    }

    public Page<Book> getAllBooks(int page, int booksPerPage, Boolean sortByYear){
        Pageable pageable = PageRequest.of(page, booksPerPage, Sort.by("publicationYear"));
        return bookRepository.findAll(pageable);
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findByTitleStartingWith(String titleStartingWith) {
        return bookRepository.findByTitleStartingWith(titleStartingWith);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(long id, Book updatedBook) {
        Book bookToUpdate = bookRepository.findById(id);

        updatedBook.setId(id);
        updatedBook.setPerson(bookToUpdate.getPerson());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void assignBook(long id, long personId) {
        Book book = bookRepository.findById(id);
        Person person = personRepository.findPersonById(personId);
        book.setPerson(person);
        book.setTakenAt(new Date());
        List<Book> personsBooks = person.getBookList();
        if (personsBooks != null) {
            personsBooks.add(book);
        } else {
            personsBooks = new ArrayList<>();
            personsBooks.add(book);
            person.setBookList(personsBooks);
        }
    }

    @Transactional
    public void releaseBook(long id, long personId) {
        var book = bookRepository.findById(id);
        var person = personRepository.findById(personId).get();
        book.setPerson(null);
        book.setTakenAt(null);
        person.getBookList().remove(book);
    }

}
