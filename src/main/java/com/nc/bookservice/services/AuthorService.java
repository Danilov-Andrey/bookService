package com.nc.bookservice.services;

 import com.nc.bookservice.entities.Copies;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.authors.AuthorNotFoundException;
import com.nc.bookservice.models.SaveBook;
import com.nc.bookservice.repos.AuthorRepo;
import com.nc.bookservice.entities.Author;
import com.nc.bookservice.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<Author> findAuthorByName(String name, int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction) {
        Page<Author> authors;
        String[] authorsName = name.split(" ");
        PageRequest request = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy));
        if (authorsName.length != 2) {
            throw new IllegalStateException("Unexpected name: " + name);
        }
        authors = authorRepo.findByLastNameAndFirstName(authorsName[0], authorsName[1], request);

        if (authors.getTotalElements() == 0){
            throw new AuthorNotFoundException("There are no any authors with firstname " + authorsName[0] + " and lastname " + authorsName[1]);
        }
         return authors;
    }

    public Page<Author> findAuthors(int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction) {
        Page<Author> authors = authorRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy)));
        if (authors.getTotalElements() == 0){
            throw new AuthorNotFoundException("There are no any authors");
        }
        return authors;
    }

    public Page<Book> findAuthorsBooks(int id, int pageNumber, int rowPerPage,  String sortBy, Sort.Direction direction) {
        return bookService.getAuthorsBooks(id, pageNumber, rowPerPage, sortBy, direction);
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
        if (author == null) {
            throw new AuthorNotFoundException("Cannot find author with id: " + id);
        }
        Publisher publisher = publisherBookCommonService.findPublisherByName(newBook.getPublisherName());
        if (publisher == null){
            publisher = new Publisher(newBook.getPublisherName());
        }
        Copies copies = new Copies(newBook.getCount(), newBook.getRate());
        Book book = new Book(author, publisher, newBook.getName(), newBook.getYear(), copies);
        return bookService.saveBook(book);
    }
}
