package com.example.LibraryFlow2.services;

import com.example.LibraryFlow2.models.Book;
import com.example.LibraryFlow2.models.Person;
import com.example.LibraryFlow2.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;


    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findPersonById(Long id) {
        return personRepository.findPersonById(id);
    }


    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person update(long id, Person person) {
        person.setId(id);
        return personRepository.save(person);
    }

    @Transactional
    public void delete(long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public List<Book> findBooksByPersonId(long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());

            person.get().getBookList().forEach(book -> {
                long diffInMillis = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if(diffInMillis > (Duration.ofDays(10).getSeconds() * 1000)) {
                    book.setExpired(true);
                }
            });
            return person.get().getBookList();
        }
        else {
            return Collections.emptyList();
        }
    }

/*   @Transactional
    public List<Book> findBooksByPersonId(long id) {
        Person person = personRepository.findPersonById(id);
        List<Book> bookList = person.getBookList();

        var today = LocalDateTime.now();

        for (Book bookFromList : bookList) {
            var takenAt = bookFromList.getTakenAt();
            if (takenAt != null) {
                LocalDateTime takenAtDateTime = takenAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                var duration = Duration.between( takenAtDateTime, today);
                if (duration.toDays() > 10) {
                    bookFromList.setExpired(true);
                }
            }
        }
        return bookList;
    }*/


}
