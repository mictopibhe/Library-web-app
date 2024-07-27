package pl.davidduke.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.davidduke.dao.BookDAO;
import pl.davidduke.dao.PersonDAO;
import pl.davidduke.models.Person;
import pl.davidduke.util.PersonValidator;

@Controller
@RequestMapping("/people")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonController {
    final PersonDAO personDAO;
    final BookDAO bookDAO;
    final PersonValidator validator;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator validator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.validator = validator;
    }

    @GetMapping
    public String getPeople(Model model) {
        model.addAttribute("people", personDAO.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult) {
        validator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("id") long id) {
        validator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        model.addAttribute("books", bookDAO.findBooksByPersonId(id));
        return "people/show";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               @PathVariable("id") long id, BindingResult bindingResult) {
        validator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
