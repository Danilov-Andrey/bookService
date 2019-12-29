package com.nc.bookservice.services;

 import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.authors.AuthorBooksNotFoundException;
import com.nc.bookservice.exceptions.books.BookNotFoundException;
import com.nc.bookservice.models.SaveBook;
import com.nc.bookservice.models.UpdateBook;
import com.nc.bookservice.entities.Author;
import com.nc.bookservice.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookService {
    private BookRepo bookRepo;
    private PublisherBookCommonService publisherBookCommonService;

    @Autowired
    public BookService(PublisherBookCommonService publisherBookCommonService, BookRepo bookRepo){
        this.publisherBookCommonService = publisherBookCommonService;
        this.bookRepo = bookRepo;
    }

    public Page<Book> findBooksByName(String name, int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction) {
        Page<Book> books = bookRepo.findByName(name, PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy)));
        if (books.getTotalElements() == 0){
            throw new BookNotFoundException("Cannot find books with name: " + name);
        }
         return books;
    }

    public Page<Book> findBooks(int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction){
        Page<Book> books = bookRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy)));
        if (books.getTotalElements() == 0){
            throw new BookNotFoundException("There are no any books");
        }
        return books;
    }

    public Book saveBook(SaveBook newBook) {
        Author author = new Author(newBook.getAuthorFirstName(), newBook.getAuthorLastName());
        Publisher publisher = publisherBookCommonService.findPublisherByName(newBook.getPublisherName());
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

    public Book updateBook(int id, UpdateBook book) {
        Book updatedBook = bookRepo.findById(id).orElse(null);
        if (updatedBook == null) {
            throw new BookNotFoundException("Cannot find book with id: " + id);
        }
        updatedBook.setName(book.getName());
        updatedBook.setYear(book.getYear());
        return bookRepo.save(updatedBook);
    }

    public void deleteBookById(int id) {
        if (!bookRepo.existsById(id)) {
            throw new BookNotFoundException("Cannot find book with id: " + id);
        }
        bookRepo.deleteById(id);
    }

    public Page<Book> getAuthorsBooks(int id, int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction) {
        Page<Book> books = bookRepo.findByAuthor_Id(id, PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy)));
        if (books.getTotalElements() == 0){
            throw new AuthorBooksNotFoundException("Cannot find any books of this author");
        }
        return books;
    }

    public Book getBookByCopiesId(int id) {
        Book book = bookRepo.findByCopies_Id(id);
        if (book == null){
            throw new AuthorBooksNotFoundException("Cannot find book with copies id " + id);
        }
        return book;
    }
}
