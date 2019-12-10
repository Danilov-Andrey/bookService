package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.PublisherIsNotFoundException;
import com.nc.bookservice.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {

    private PublisherRepo publisherRepo;

    private BookService bookService;

    @Autowired
    public PublisherService(@Lazy BookService bookService, PublisherRepo publisherRepo){
        this.bookService = bookService;
        this.publisherRepo = publisherRepo;
    }

    public Publisher findById(int id) {
        Publisher publisher = publisherRepo.findById(id).orElse(null);
        if (publisher == null){
            throw new PublisherIsNotFoundException("Cannot find publisher with id " + id);
        }
        return publisher;
    }

    public DataPagination<Publisher> findAll(int pageNumber, int rowPerPage){
        List<Publisher> publishers = new ArrayList<>();
        int totalPage = (int) Math.ceil((float)publisherRepo.count()/rowPerPage);
        publisherRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(publishers::add);
        DataPagination<Publisher> dataPagination = new DataPagination(totalPage, pageNumber, publishers);
        return dataPagination;
    }

    public Publisher savePublisher(Publisher newPublisher) {
        return publisherRepo.save(newPublisher);
    }

    public Publisher updatePublisher(int id, Publisher publisher) {
        Publisher updatedPublisher = findById(id);
        updatedPublisher.setName(publisher.getName());
        return publisherRepo.save(updatedPublisher);
    }

    public void deleteById(int id) {
        if(!publisherRepo.existsById(id)) {
            throw new PublisherIsNotFoundException("Cannot find publisher with id " + id);
        }
        publisherRepo.deleteById(id);
    }

    public DataPagination<Book> findPublishersBooks(int id, int pageNumber, int rowPerPage) {
        return bookService.getPublishersBooks(id, pageNumber, rowPerPage);
    }

    public Publisher findByName(String publisherName){
        Publisher publisher = publisherRepo.findByName(publisherName);
        return publisher;
    }
}

