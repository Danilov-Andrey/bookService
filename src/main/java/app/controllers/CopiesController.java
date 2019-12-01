package app.controllers;

import app.entities.Copies;
import app.services.CopiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "copies")
public class CopiesController {
    @Autowired
    private CopiesService copiesService;

    @GetMapping()
    public @ResponseBody Iterable<Copies> getAllCopies(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage)
    {
        return copiesService.findAll(pageNumber, rowPerPage);
    }

    @GetMapping(path = "{id}")
    public @ResponseBody
    Copies getCopies(@PathVariable int id) throws Exception{
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
