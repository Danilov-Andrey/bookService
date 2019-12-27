package com.nc.bookservice.controllers;

 import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
 import com.nc.bookservice.exceptions.publisher.PublisherExistsException;
 import com.nc.bookservice.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public  ResponseEntity<?> getPublishers(
            @RequestParam (value = "name") Optional<String> name,
            @RequestParam (value = "pageNumber") Optional<Integer> pageNumber,
            @RequestParam (value = "rowPerPage") Optional<Integer> rowPerPage,
            @RequestParam (value = "sortBy") Optional<String> sortBy,
            @RequestParam (value = "direction") Optional<Sort.Direction> direction)
    {
        Page<Publisher> publishers;
        Publisher publisher;
        try{
            if (name.isEmpty()) {
                publishers = publisherService.findAllPublishers(pageNumber.get(), rowPerPage.get(), sortBy.get(), direction.get());
                return new ResponseEntity<>(publishers, HttpStatus.OK);
            }
            publisher = publisherService.findPublisherByName(name.get());
            return new ResponseEntity<>(publisher, HttpStatus.OK);
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
        }  catch (RuntimeException e){
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
        } catch (PublisherExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable int id) {
        try{
            publisherService.deletePublisherById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getPublisher(@PathVariable int id) {
        try {
            Publisher publisher = publisherService.findPublisherById(id);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/{id}/books")
    public ResponseEntity<?> getPublishersBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage,
            @RequestParam String sortBy,
            @RequestParam Sort.Direction direction)
    {
        try {
            Page<Book> books = publisherService.findPublishersBooks(id, pageNumber, rowPerPage, sortBy, direction);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
