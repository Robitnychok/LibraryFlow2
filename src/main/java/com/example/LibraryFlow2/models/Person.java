package com.example.LibraryFlow2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Please enter your first and last name")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "fullname")
    private String fullName;

    @Min(value = 1700, message = "Year of birth cannot be earlier than 1700")
    @Max(value = 2025, message = "Year of birth cannot be in the future")
    @Column(name = "birthyear")
    private int birthYear;

    @OneToMany(mappedBy = "person")
    private List<Book> bookList;


    public Person(){};

    public Person(long id, String fullName, int birthYear){
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Min(value = 1700, message = "Year of birth cannot be earlier than 1700")
    @Max(value = 2025, message = "Year of birth cannot be in the future")
    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(@Min(value = 1700, message = "Year of birth cannot be earlier than 1700") @Max(value = 2025, message = "Year of birth cannot be in the future") int birthYear) {
        this.birthYear = birthYear;
    }

    public @NotEmpty(message = "Please enter your first and last name") @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters") String getFullName() {
        return fullName;
    }

    public void setFullName(@NotEmpty(message = "Please enter your first and last name") @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters") String fullName) {
        this.fullName = fullName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
