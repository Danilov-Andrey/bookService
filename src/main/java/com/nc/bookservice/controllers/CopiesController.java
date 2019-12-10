package com.nc.bookservice.controllers;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.services.CopiesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public DataPagination<Copies> getAllCopies(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage)
    {
        return copiesService.findAll(pageNumber, rowPerPage);
    }

    @GetMapping(path = "/{id}")
    public Copies getCopies(@PathVariable int id) {
            return copiesService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Copies> updateCopies(
            @PathVariable("id") int id,
            @RequestBody Copies newCopies
    ){
            Copies copies = copiesService.updateCopies(id, newCopies);
            return new ResponseEntity<>(copies, HttpStatus.OK);
    }
}
