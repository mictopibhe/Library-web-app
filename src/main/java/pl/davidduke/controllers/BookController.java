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
import pl.davidduke.models.Book;
import pl.davidduke.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {
    final PersonDAO personDAO;
    final BookDAO bookDAO;

    @Autowired
    public BookController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String saveBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.findById(id));
        return "books/edit";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.findById(id));
        Optional<Person> owner = bookDAO.getBookOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.findAll());
        }
        return "books/show";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             @PathVariable("id") long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") long id, @ModelAttribute Person person) {
        bookDAO.assign(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") long id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
}
