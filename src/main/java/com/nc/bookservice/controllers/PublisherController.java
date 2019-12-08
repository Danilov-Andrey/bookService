package com.nc.bookservice.controllers;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping(path = "/publishers")
public class PublisherController {
    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    @GetMapping()
    public  DataPagination<Publisher> getAllPublishers(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) {
        return publisherService.findAll(pageNumber, rowPerPage);
    }

    @PostMapping
    public  ResponseEntity<Publisher> addNewPublisher(
            @RequestBody Publisher newPublisher
    ) throws Exception {
        try{
            Publisher publisher = publisherService.save(newPublisher);
            return ResponseEntity.created(new URI("publisher/" + publisher.getId())).body(publisher);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> updatePublisher(
            @PathVariable("id") int id,
            @RequestBody Publisher newPublisher
    ) throws Exception {
        try{
            publisherService.updatePublisher(id, newPublisher);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable int id) throws Exception{
        try {
            publisherService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path="{id}")
    public Publisher getPublisher(@PathVariable int id) throws Exception {
        try{
            return publisherService.findById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(path="{id}/books")
    public Iterable<Book> getAllPublishersBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) throws Exception {
        return publisherService.findPublishersBooks(id, pageNumber, rowPerPage);
    }
}
