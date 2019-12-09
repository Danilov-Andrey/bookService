package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.models.SaveBook;
import com.nc.bookservice.models.UpdateBook;
import com.nc.bookservice.entities.Author;
import com.nc.bookservice.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private BookRepo bookRepo;
    private PublisherService publisherService;

    @Autowired
    public BookService(@Lazy PublisherService publisherService, BookRepo bookRepo){
        this.publisherService = publisherService;
        this.bookRepo = bookRepo;
    }

    public Book findById(int id) throws Exception{
        Book book = bookRepo.findById(id).orElse(null);
        if (book == null){
            throw new Exception("Cannot find book with id: " + id);
        }
        return book;
    }

    public DataPagination<Book> findAll(int pageNumber, int rowPerPage){
        List<Book> books = new ArrayList<>();
        bookRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(books::add);
        int totalPage = (int) Math.ceil((float)bookRepo.count()/rowPerPage);
        DataPagination dataPagination = new DataPagination(totalPage, pageNumber, books);
        return dataPagination;
    }

    public Book saveBook(SaveBook newBook) {
        Author author = new Author(newBook.getAuthorFirstName(), newBook.getAuthorLastName());
        Publisher publisher = publisherService.findByName(newBook.getPublisherName());
        if (publisher == null){
            publisher = new Publisher(newBook.getPublisherName());
        }
        Copies copies = new Copies(newBook.getCount(),newBook.getRate());
        Book book = new Book(author, publisher, newBook.getName(), newBook.getYear(), copies);
        return bookRepo.save(book);
    }

    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    public void updateBook(int id, UpdateBook book) throws Exception {
        Book updatedBook = findById(id);
        if (updatedBook == null) {
            throw new Exception("Cannot find book with id: " + id);
        }
        updatedBook.setName(book.getName());
        updatedBook.setYear(book.getYear());
        bookRepo.save(updatedBook);
    }

    public void deleteById(int id) throws Exception {
        if (!bookRepo.existsById(id)) {
            throw new Exception("Cannot find book with id: " + id);
        }
        bookRepo.deleteById(id);
    }

    public DataPagination<Book> getAuthorsBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        List<Book> books = bookRepo.findByAuthor_Id(id, PageRequest.of(pageNumber - 1, rowPerPage));
        List<Book> allBooks = bookRepo.findByAuthor_Id(id);
        int totalPage = (int) Math.ceil((float)allBooks.size()/rowPerPage);
        if (books.size() == 0){
            throw new Exception("Cannot find any books of this author");
        }
        DataPagination<Book> dataPagination = new DataPagination<>(totalPage, pageNumber, books);
        return dataPagination;
    }

    public DataPagination<Book> getPublishersBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        List<Book> books = bookRepo.findByPublisher_Id(id, PageRequest.of(pageNumber - 1, rowPerPage));
        List<Book> allBooks = bookRepo.findByPublisher_Id(id);
        int totalPage = (int) Math.ceil((float)allBooks.size()/rowPerPage);
        if (books.size() == 0){
            throw new Exception("Cannot find any books of this publisher");
        }
        DataPagination<Book> dataPagination = new DataPagination<>(totalPage, pageNumber, books);
        return dataPagination;
    }
}
