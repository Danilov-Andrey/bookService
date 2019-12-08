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
@RequestMapping(path = "copies")
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

    @GetMapping(path = "{id}")
    public Copies getCopies(@PathVariable int id) throws Exception{
        try {
            return copiesService.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> updateCopies(
            @PathVariable("id") int id,
            @RequestBody Copies copies
    ) throws Exception {
        try{
            copiesService.updateCopies(id, copies);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
