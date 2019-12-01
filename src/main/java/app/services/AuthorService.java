package app.services;

import app.entities.Author;
import app.entities.Book;
import app.entities.Copies;
import app.entities.Publisher;
import app.repos.AuthorRepo;
import app.repos.BookRepo;
import app.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Autowired
    private BookService bookService;

    private boolean existsById(int id) {
        return authorRepo.existsById(id);
    }

    public Author findById(int id) throws Exception {
        Author author = authorRepo.findById(id).orElse(null);
        if (author==null) {
            throw new Exception("Cannot find author with id: " + id);
        }
        else return author;
    }

    public List<Author> findAll(int pageNumber, int rowPerPage) {
        List<Author> authors = new ArrayList<>();
        authorRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(authors::add);
        return authors;
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
        if (!existsById(id)) {
            throw new Exception("Cannot find author with id: " + id);
        }
        else {
            authorRepo.deleteById(id);
        }
    }

    public void addNewBook(int id, String name, int year, int count, int rate, String publisherName) throws Exception {
        Author author = findById(id);
        Publisher publisher = publisherRepo.findByName(publisherName);
        Copies copies = new Copies(count, rate);
        if (publisher == null){
            publisher = new Publisher(publisherName);
        }
        Book book = new Book(author, publisher, name, year, copies);
        bookService.save(book);
    }
}
