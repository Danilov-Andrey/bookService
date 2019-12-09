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
    public Author getAuthor(@PathVariable int id) throws Exception {
        try{
            return authorService.findById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor (
            @RequestBody Author newAuthor
    ) throws Exception {
        try {
            Author author = authorService.saveAuthor(newAuthor);
            return ResponseEntity.created(new URI("/api/authors/" + author.getId()))
                    .body(author);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addAuthorsBook(
            @PathVariable("id") int id,
            @RequestBody SaveBook newBook
            ) throws Exception {
        try{
            authorService.addNewBook(id, newBook);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAuthor(
            @PathVariable("id") int id,
            @RequestBody Author newAuthor
    ) throws Exception {
        try{
            authorService.updateAuthor(id, newAuthor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable int id) throws Exception {
        try {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
