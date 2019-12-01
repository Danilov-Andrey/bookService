package app.services;

import app.entities.Author;
import app.entities.Book;
import app.entities.Copies;
import app.entities.Publisher;
import app.entities.update.BookUpdate;
import app.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;

    private boolean existsById(int id){
        return bookRepo.existsById(id);
    }

    public Book findById(int id) throws Exception{
        Book book = bookRepo.findById(id).orElse(null);
        if (book == null){
            throw new Exception("Cannot find book with id: " + id);
        }
        else return book;
    }

    public List<Book> findAll(int pageNumber, int rowPerPage){
        List<Book> books = new ArrayList<>();
        bookRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(books::add);
        return books;
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
        if (updatedBook != null){
            updatedBook.setName(book.getName());
            updatedBook.setYear(book.getYear());
            bookRepo.save(updatedBook);
        } else {
            throw new Exception("Cannot find book with id: " + id);
        }
    }

    public void deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find book with id: " + id);
        }
        else {
            bookRepo.deleteById(id);
        }
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
