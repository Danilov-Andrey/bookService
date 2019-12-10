package com.nc.bookservice.controllers;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Author;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.models.SaveBook;
import com.nc.bookservice.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(path="/api/authors")
public class AuthorController {
    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public DataPagination<Author> getAllAuthors(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) {
        return authorService.findAll(pageNumber,rowPerPage);
    }

    @GetMapping(path = "/{id}/books")
    public DataPagination<Book> getAuthorsBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) throws Exception {
        return authorService.findAuthorsBooks(id, pageNumber, rowPerPage);
    }

    @GetMapping(path="/{id}")
    public Author getAuthor(@PathVariable int id) {
           return authorService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor (
            @RequestBody Author newAuthor
    ) {
       Author author = authorService.saveAuthor(newAuthor);
       return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Book> addAuthorsBook(
            @PathVariable("id") int id,
            @RequestBody SaveBook newBook
            ){
            Book book = authorService.addNewBook(id, newBook);
            return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable("id") int id,
            @RequestBody Author newAuthor
    ) {
            Author author = authorService.updateAuthor(id, newAuthor);
            return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
