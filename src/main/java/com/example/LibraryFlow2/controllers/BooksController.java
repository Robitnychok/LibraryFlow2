package com.example.LibraryFlow2.controllers;

import com.example.LibraryFlow2.models.Book;
import com.example.LibraryFlow2.models.Person;
import com.example.LibraryFlow2.services.BookService;
import com.example.LibraryFlow2.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @RequestMapping
    public String getAll(Model model,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                         @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear
    ) {
        if(page != null && booksPerPage != null && sortByYear != null && sortByYear){
            model.addAttribute("books", bookService.getAllBooks(page, booksPerPage, sortByYear));
        } else if(page != null && booksPerPage != null){
            model.addAttribute("books", bookService.getAllBooks(page, booksPerPage));
        } else if(sortByYear != null && sortByYear){
            model.addAttribute("books", bookService.getAllBooks(sortByYear));
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        return "books/allBooks";
    }

    @RequestMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personService.findAll());
        if (book.getPerson() != null){
            Person owner = personService.findPersonById(book.getPerson().getId());
            model.addAttribute("book_owner", owner);
        }

        return "books/show";
    }

    @PostMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") long id, @RequestParam("person") long personId){
        bookService.assignBook(id, personId);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") long id, @RequestParam("ownerId") long personId){
        bookService.releaseBook(id, personId);
        return "redirect:/books/" + id;
    }

    @RequestMapping("/create")
    public String create(Model model){
        model.addAttribute("books", new Book());
        return "books/create";
    }

    @PostMapping()
    public String saveBook(@ModelAttribute ("books") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/create";

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @RequestMapping("/{id}/update")
    public String update(Model model, @PathVariable("id") long id){
        model.addAttribute("books", bookService.getBookById(id));
        return "books/update";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute ("books") @Valid Book book, BindingResult bindingResult, @PathVariable("id") long id){
        if(bindingResult.hasErrors())
            return "/books/update";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id, Model model){
        bookService.deleteById(id);
        return "redirect:/books";
    }


    @GetMapping("/search")
    public String searchBooks(
            @RequestParam(value = "titleStartingWith", required = false) String titleStartingWith,
            Model model
    ) {
        if (titleStartingWith != null && !titleStartingWith.isEmpty()) {
            model.addAttribute("books", bookService.findByTitleStartingWith(titleStartingWith));
        } else {
            model.addAttribute("books", List.of());
        }
        return "books/search";
    }
}


