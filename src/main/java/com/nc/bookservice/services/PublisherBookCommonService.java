package com.nc.bookservice.services;

import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.publisher.PublisherBooksNotFoundException;
import com.nc.bookservice.repos.BookRepo;
import com.nc.bookservice.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PublisherBookCommonService {
    private BookRepo bookRepo;
    private PublisherRepo publisherRepo;

    @Autowired
    public PublisherBookCommonService(BookRepo bookRepo, PublisherRepo publisherRepo){
        this.bookRepo = bookRepo;
        this.publisherRepo= publisherRepo;
    }

    public Publisher findPublisherByName(String publisherName){
        Publisher publisher = publisherRepo.findByName(publisherName);
        return publisher;
    }

    public Page<Book> getPublishersBooks(int id, int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction) {
        Page<Book> books = bookRepo.findByPublisher_Id(id, PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy)));
        if (books.getTotalElements() == 0){
            throw new PublisherBooksNotFoundException("Cannot find any books of this publisher");
        }
        return books;
    }

}
