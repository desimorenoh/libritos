package org.factoriaf5.libritos.controllers;

import org.factoriaf5.libritos.repositories.Book;
import org.factoriaf5.libritos.repositories.BookRepository;
import org.factoriaf5.libritos.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/books")
    String listBooks(Model model, @RequestParam(required = false) String category) {

        model.addAttribute("title", "Book list");
        model.addAttribute("books", getBooks(category));
        model.addAttribute("categories", categoryRepository.findAll());
        return "books/all";
    }

    @GetMapping("/books/new")
    String newBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("title", "Create new book");
        return "books/edit";
    }

    @PostMapping("/books/new")
    String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    String editBook(Model model, @PathVariable Long id) {
        Book book = bookRepository.findById(id).get();
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("title", "Edit book");
        return "books/edit";
    }

    @GetMapping("/books/delete/{id}")
    String remove(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/home")
    String listBooksOnHome(Model model, @RequestParam(required = false) String category) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("title", "Book list");
        model.addAttribute("books", books);
        return "books/front";
    }

    @GetMapping("/books/search")
    String searchBook(@RequestParam String word, Model model) {
        List<Book> books = bookRepository.findBookByTitleContaining(word);
        model.addAttribute("title", String.format("Books containing \"%s\"", word));
        model.addAttribute("books", books);
        model.addAttribute("categories", categoryRepository.findAll());

        return "books/front";
    }


    private List<Book> getBooks(String category) {
        if (category == null) {
            return bookRepository.findAll();
        }
        return bookRepository.findBookByCategoryEquals(category);
    }

}
