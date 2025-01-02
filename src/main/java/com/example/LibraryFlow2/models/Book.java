package com.example.LibraryFlow2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Please enter the title of the book")
    @Size(min = 1, max = 50, message = "The title of the book should be between 1 and 50 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Please identify the author of the book")
    @Size(min = 2, max = 50, message = "The author of the book should be between 2 and 50 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 100, message = "The year of publication cannot be earlier than 100")
    @Max(value = 2025, message = "The year of publication cannot be in the future")
    @Column(name = "publicationyear")
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person person;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;


    public Book(){};

    public Book (long id, String title, String author, int publicationYear){
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Please enter the title of the book") @Size(min = 1, max = 50, message = "The title of the book should be between 1 and 50 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Please enter the title of the book") @Size(min = 1, max = 50, message = "The title of the book should be between 1 and 50 characters") String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Please identify the author of the book") @Size(min = 2, max = 50, message = "The author of the book should be between 2 and 50 characters") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotEmpty(message = "Please identify the author of the book") @Size(min = 2, max = 50, message = "The author of the book should be between 2 and 50 characters") String author) {
        this.author = author;
    }

    @Min(value = 100, message = "The year of publication cannot be earlier than 100")
    @Max(value = 2025, message = "The year of publication cannot be in the future")
    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(@Min(value = 100, message = "The year of publication cannot be earlier than 100") @Max(value = 2025, message = "The year of publication cannot be in the future") int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
