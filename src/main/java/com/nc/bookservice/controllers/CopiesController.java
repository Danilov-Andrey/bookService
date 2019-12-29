package com.nc.bookservice.controllers;

import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.services.CopiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/copies")
public class CopiesController {
    private CopiesService copiesService;

    @Autowired
    public CopiesController(CopiesService copiesService){
        this.copiesService = copiesService;
    }

    @GetMapping
    public ResponseEntity<?> getCopies(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage,
            @RequestParam String sortBy,
            @RequestParam Sort.Direction direction)
    {
        try {
            Page<Copies> copies = copiesService.findCopies(pageNumber, rowPerPage, sortBy, direction);
            return new ResponseEntity<>(copies, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCopies(@PathVariable int id) {
        try {
            Copies copies = copiesService.findCopiesById(id);
            return new ResponseEntity<>(copies, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}/books")
    public ResponseEntity<?> getBookByCopiesId(
            @PathVariable("id") int id
    ) {
        try {
            Book book = copiesService.findBookByCopiesId(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCopies(
            @PathVariable("id") int id,
            @RequestBody Copies newCopies
    ){
        try {
            Copies copies = copiesService.updateCopies(id, newCopies);
            return new ResponseEntity<>(copies, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
