package com.nc.bookservice.controllers;

import com.nc.bookservice.entities.update.BookUpdate;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping(path="/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping()
    public @ResponseBody Iterable<Book> getAllBooks(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) {
        return bookService.findAll(pageNumber,rowPerPage);
    }

    @GetMapping(path="{id}")
    public @ResponseBody
    Book getBook(@PathVariable int id) throws Exception {
        try{
            return bookService.findById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping(path="{id}")
    public @ResponseBody
    ResponseEntity<Void> deleteBook(@PathVariable int id) throws Exception {
        try {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable("id") int id,
            @RequestBody BookUpdate book
    ) throws Exception {
        try{
            bookService.updateBook(id, book);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("create")
    public @ResponseBody
    ResponseEntity<Book> addNewBook (
            @RequestParam int year,
            @RequestParam String name,
            @RequestParam String authorFirstName,
            @RequestParam String authorLastName,
            @RequestParam String publisherName,
            @RequestParam int rate,
            @RequestParam int count
    ) throws Exception {
        try {
            Book book = bookService.save(authorFirstName ,authorLastName, publisherName, year, name, rate, count);
            return ResponseEntity.created(new URI("book/" + book.getId()))
                    .body(book);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
