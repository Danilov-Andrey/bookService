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
    public  DataPagination<Publisher> getAllPublishers(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage)
    {
            return publisherService.findAll(pageNumber, rowPerPage);
    }

    @PostMapping
    public  ResponseEntity<Publisher> addNewPublisher(
            @RequestBody Publisher newPublisher
    ) {
            Publisher publisher = publisherService.savePublisher(newPublisher);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(
            @PathVariable("id") int id,
            @RequestBody Publisher newPublisher
    ) {
            Publisher publisher =  publisherService.updatePublisher(id, newPublisher);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable int id) {
            publisherService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public Publisher getPublisher(@PathVariable int id) {
            return publisherService.findById(id);
    }

    @GetMapping(path="/{id}/books")
    public DataPagination<Book> getAllPublishersBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage)
    {
            return publisherService.findPublishersBooks(id, pageNumber, rowPerPage);
    }
}
