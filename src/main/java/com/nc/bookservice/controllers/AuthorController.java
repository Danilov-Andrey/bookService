package com.nc.bookservice.controllers;

 import com.nc.bookservice.entities.Author;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.models.SaveBook;
import com.nc.bookservice.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 import java.util.Optional;

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
    public ResponseEntity<?> getAuthors(
            @RequestParam (value = "name") Optional<String> name,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage,
            @RequestParam String sortBy,
            @RequestParam Sort.Direction direction) {
        try {
            Page<Author> authors;
            if (name.isEmpty()){
                authors = authorService.findAuthors(pageNumber,rowPerPage,sortBy,direction);
            } else {
                authors = authorService.findAuthorByName(name.get(), pageNumber,rowPerPage,sortBy,direction);
            }
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}/books")
    public ResponseEntity<?> getAuthorsBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage,
            @RequestParam String sortBy,
            @RequestParam Sort.Direction direction) {
        try {
            Page<Book> books = authorService.findAuthorsBooks(id, pageNumber, rowPerPage, sortBy, direction);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable int id) {
        try{
            Author author = authorService.findById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor (
            @RequestBody Author newAuthor
    ) {
           Author author = authorService.saveAuthor(newAuthor);
           return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addAuthorsBook(
            @PathVariable("id") int id,
            @RequestBody SaveBook newBook
            ){
        try {
            Book book = authorService.addNewBook(id, newBook);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAuthor(
            @PathVariable("id") int id,
            @RequestBody Author newAuthor
    ) {
        try {
            Author author = authorService.updateAuthor(id, newAuthor);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        try {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
