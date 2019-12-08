package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.models.BookUpdate;
import com.nc.bookservice.entities.Author;
import com.nc.bookservice.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo){
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

    public Book save(String authorFirstName,
                     String authorLastName,
                     String publisherName,
                     int year,
                     String name,
                     int rate,
                     int count) {
        Author author = new Author(authorFirstName,authorLastName);
        Publisher publisher = new Publisher(publisherName);
        Copies copies = new Copies(count,rate);
        Book book = new Book(author, publisher, name, year, copies);
        return bookRepo.save(book);
    }

    public Book save(Book book) {
        return bookRepo.save(book);
    }

    public void updateBook(int id, BookUpdate book) throws Exception {
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

    public List<Book> authorsBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        List<Book> books = bookRepo.findByAuthor_Id(id, PageRequest.of(pageNumber - 1, rowPerPage));
        if (books.size() == 0){
            throw new Exception("Cannot find any books of this author");
        }
        return books;
    }

    public List<Book> publishersBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        List<Book> books = bookRepo.findByPublisher_Id(id, PageRequest.of(pageNumber - 1, rowPerPage));
        if (books.size() == 0){
            throw new Exception("Cannot find any books of this publisher");
        }
        return books;
    }
}
