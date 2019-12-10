package com.nc.bookservice.controllers;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.models.SaveBook;
import com.nc.bookservice.models.UpdateBook;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="/api/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController( BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) {
        try{
            DataPagination<Book> books = bookService.findAllBooks(pageNumber,rowPerPage);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getBook(@PathVariable int id) {
            try{
                Book book = bookService.findById(id);
                return new ResponseEntity<>(book, HttpStatus.OK);
            } catch (RuntimeException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        try {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable("id") int id,
            @RequestBody UpdateBook updateBook
    ) {
        try {
            Book book = bookService.updateBook(id, updateBook);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook (
            @RequestBody SaveBook newBook
    ) {
            Book book = bookService.saveBook(newBook);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
}
