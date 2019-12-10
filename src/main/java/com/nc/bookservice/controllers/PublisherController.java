package com.nc.bookservice.controllers;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/publishers")
public class PublisherController {
    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    @GetMapping
    public  ResponseEntity<?> getAllPublishers(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage)
    {
        try{
            DataPagination<Publisher> publishers = publisherService.findAll(pageNumber, rowPerPage);
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public  ResponseEntity<?> addNewPublisher(
            @RequestBody Publisher newPublisher
    ) {
        try {
            Publisher publisher = publisherService.savePublisher(newPublisher);
            return new ResponseEntity<>(publisher, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(
            @PathVariable("id") int id,
            @RequestBody Publisher newPublisher
    ) {
        try {
            Publisher publisher =  publisherService.updatePublisher(id, newPublisher);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable int id) {
        try{
            publisherService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getPublisher(@PathVariable int id) {
        try {
            Publisher publisher = publisherService.findById(id);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/{id}/books")
    public ResponseEntity<?> getAllPublishersBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage)
    {
        try {
            DataPagination<Book> books = publisherService.findPublishersBooks(id, pageNumber, rowPerPage);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
