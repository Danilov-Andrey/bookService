package app.controllers;

import app.entities.Author;
import app.entities.Book;
import app.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@Controller
@RequestMapping(path="/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public @ResponseBody Iterable<Author> getAllAuthors(
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) {
        return authorService.findAll(pageNumber,rowPerPage);
    }

    @GetMapping(path = "{id}/books")
    public @ResponseBody Iterable<Book> getAuthorsBooks(
            @PathVariable("id") int id,
            @RequestParam int pageNumber,
            @RequestParam int rowPerPage) throws Exception {
        return authorService.findAuthorsBooks(id, pageNumber, rowPerPage);
    }

    @GetMapping(path="{id}")
    public @ResponseBody
    Author getAuthor(@PathVariable int id) throws Exception {
        try{
            return authorService.findById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("create")
    public @ResponseBody
    ResponseEntity<Author> createNewAuthor (
            @RequestBody Author newAuthor
    ) throws Exception {
        try {
            Author author = authorService.save(newAuthor);
            return ResponseEntity.created(new URI("author/" + author.getId()))
                    .body(author);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("{id}/createBook")
    public ResponseEntity<String> addNewBook(
            @PathVariable("id") int id,
            @RequestParam String name,
            @RequestParam int year,
            @RequestParam int count,
            @RequestParam int rate,
            @RequestParam String publisherName
    ) throws Exception {
        try{
            authorService.addNewBook(id, name, year, count, rate, publisherName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> updateAuthor(
            @PathVariable("id") int id,
            @RequestBody Author newAuthor
    ) throws Exception {
        try{
            authorService.updateAuthor(id, newAuthor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping(path="{id}")
    public @ResponseBody
    ResponseEntity<String> deleteAuthor(@PathVariable int id) throws Exception {
        try {
            authorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
