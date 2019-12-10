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
    public DataPagination<Book> getAllBooks(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) {
        return bookService.findAll(pageNumber,rowPerPage);
    }

    @GetMapping(path="/{id}")
    public Book getBook(@PathVariable int id) {
            return bookService.findById(id);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable("id") int id,
            @RequestBody UpdateBook updateBook
    ) {
            Book book = bookService.updateBook(id, updateBook);
            return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook (
            @RequestBody SaveBook newBook
    ) {
            Book book = bookService.saveBook(newBook);
            return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
