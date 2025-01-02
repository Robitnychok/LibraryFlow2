package com.example.LibraryFlow2.controllers;

import com.example.LibraryFlow2.models.Person;
import com.example.LibraryFlow2.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("person", personService.findAll());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("person", personService.findPersonById(id));
        model.addAttribute("books", personService.findBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/create")
    public String create (Model model){
        model.addAttribute("person", new Person());
        return "people/create";
    }

    @PostMapping
    public String savePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/create";
        }

        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/update")
    public String update (@PathVariable("id") long id, Model model){
        model.addAttribute("person", personService.findPersonById(id));
        return "people/update";
    }

    @PatchMapping("/{id}")
    public String updatePerson( @ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") long id){

        if(bindingResult.hasErrors())
            return "people/update";

        personService.update(id, person);
        return ("redirect:/people");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id, Model model){
        personService.delete(id);
        return "redirect:/people";
    }

}
