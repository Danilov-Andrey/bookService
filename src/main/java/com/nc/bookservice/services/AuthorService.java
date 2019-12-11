package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.authors.AuthorNotFoundException;
import com.nc.bookservice.models.SaveBook;
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
    private PublisherBookCommonService publisherBookCommonService;
    private BookService bookService;

    @Autowired
    public AuthorService(AuthorRepo authorRepo, PublisherBookCommonService publisherBookCommonService, BookService bookService){
     this.authorRepo = authorRepo;
     this.publisherBookCommonService = publisherBookCommonService;
     this.bookService = bookService;
    }

    public Author findById(int id) {
        Author author = authorRepo.findById(id).orElse(null);
        if (author == null) {
            throw new AuthorNotFoundException("Cannot find author with id: " + id);
        }
        return author;
    }

    public DataPagination<Author> findAllAuthors(int pageNumber, int rowPerPage) {
        List<Author> authors = new ArrayList<>();
        authorRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(authors::add);
        if (authors.size() == 0){
            throw new AuthorNotFoundException("There are no any authors");
        }
        int totalPage = (int) Math.ceil((float)authorRepo.count()/rowPerPage);
        DataPagination<Author> dataPagination = new DataPagination(totalPage, pageNumber, authors);
        return dataPagination;
    }

    public DataPagination<Book> findAuthorsBooks(int id, int pageNumber, int rowPerPage) {
        return bookService.getAuthorsBooks(id, pageNumber, rowPerPage);
    }

    public Author saveAuthor(Author newAuthor) {
         return authorRepo.save(newAuthor);
    }

    public Author updateAuthor(int id, Author author) {
        Author updatedAuthor = findById(id);
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());
        return authorRepo.save(updatedAuthor);
    }

    public void deleteById(int id) {
        if (!authorRepo.existsById(id)) {
            throw new AuthorNotFoundException("Cannot find author with id: " + id);
        }
        authorRepo.deleteById(id);
    }

    public Book addNewBook(int id, SaveBook newBook) {
        Author author = findById(id);
        Publisher publisher = publisherBookCommonService.findByName(newBook.getPublisherName());
        if (publisher == null){
            publisher = new Publisher(newBook.getPublisherName());
        }
        Copies copies = new Copies(newBook.getCount(), newBook.getRate());
        Book book = new Book(author, publisher, newBook.getName(), newBook.getYear(), copies);
        return bookService.saveBook(book);
    }
}
