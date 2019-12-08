package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.repos.AuthorRepo;
import com.nc.bookservice.entities.Author;
import com.nc.bookservice.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private AuthorRepo authorRepo;

    private PublisherService publisherService;

    private BookService bookService;

    @Autowired
    public AuthorService(AuthorRepo authorRepo, PublisherService publisherService, BookService bookService){
     this.authorRepo = authorRepo;
     this.publisherService = publisherService;
     this.bookService = bookService;
    }

    public Author findById(int id) throws Exception {
        Author author = authorRepo.findById(id).orElse(null);
        if (author==null) {
            throw new Exception("Cannot find author with id: " + id);
        }
        return author;
    }

    public DataPagination<Author> findAll(int pageNumber, int rowPerPage) {
        List<Author> authors = new ArrayList<>();
        int totalPage = (int) Math.ceil((float)authorRepo.count()/rowPerPage);
        authorRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(authors::add);
        DataPagination dataPagination = new DataPagination(totalPage, pageNumber, authors);
        return dataPagination;
    }

    public List<Book> findAuthorsBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        return bookService.authorsBooks(id, pageNumber, rowPerPage);
    }

    public Author save(Author newAuthor) {
         return authorRepo.save(newAuthor);
    }

    public void updateAuthor(int id, Author author) throws Exception {
        Author updatedAuthor = findById(id);
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());
        authorRepo.save(updatedAuthor);
    }

    public void deleteById(int id) throws Exception {
        if (!authorRepo.existsById(id)) {
            throw new Exception("Cannot find author with id: " + id);
        }
        authorRepo.deleteById(id);
    }

    public void addNewBook(int id, String name, int year, int count, int rate, String publisherName) throws Exception {
        Author author = findById(id);
        Publisher publisher = publisherService.findByName(publisherName);
        Copies copies = new Copies(count, rate);
        if (publisher == null){
            publisher = new Publisher(publisherName);
        }
        Book book = new Book(author, publisher, name, year, copies);
        bookService.save(book);
    }
}
