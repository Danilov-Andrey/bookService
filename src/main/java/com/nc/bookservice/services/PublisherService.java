package com.nc.bookservice.services;

import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.publisher.PublisherExistsException;
import com.nc.bookservice.exceptions.publisher.PublisherNotFoundException;
import com.nc.bookservice.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PublisherService {
    private PublisherRepo publisherRepo;
    private PublisherBookCommonService publisherBookCommonService;

    @Autowired
    public PublisherService(PublisherBookCommonService publisherBookCommonService, PublisherRepo publisherRepo){
        this.publisherBookCommonService = publisherBookCommonService;
        this.publisherRepo = publisherRepo;
    }

    public Publisher findPublisherById(int id) {
        Publisher publisher = publisherRepo.findById(id).orElse(null);
        if (publisher == null){
            throw new PublisherNotFoundException("Cannot find publisher with id " + id);
        }
        return publisher;
    }

    public Publisher findPublisherByName(String name) {
        Publisher publisher = publisherRepo.findByName(name);
        if (publisher == null){
            throw new PublisherNotFoundException("Cannot find publisher with name " + name);
        }
        return publisher;
    }

    public Page<Publisher> findAllPublishers(int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction){
        Page<Publisher> publishers = publisherRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage, Sort.by(direction, sortBy)));
        if(publishers.getTotalElements() == 0) {
            throw new PublisherNotFoundException("Cannot find any publishers");
        }
        return publishers;
    }

    public Publisher savePublisher(Publisher newPublisher) {
        Publisher dbPublisher =  publisherRepo.findByName(newPublisher.getName());
        if (dbPublisher != null){
            throw new PublisherExistsException("This publisher exists");
        }
        return publisherRepo.save(newPublisher);
    }

    public Publisher updatePublisher(int id, Publisher publisher) {
        Publisher dbPublisher =  publisherRepo.findByName(publisher.getName());
        if (dbPublisher != null ){
            throw new PublisherExistsException("This publisher exists");
        }
        Publisher updatedPublisher = findPublisherById(id);
        updatedPublisher.setName(publisher.getName());
        return publisherRepo.save(updatedPublisher);
    }

    public void deletePublisherById(int id) {
        if(!publisherRepo.existsById(id)) {
            throw new PublisherNotFoundException("Cannot find publisher with id " + id);
        }
        publisherRepo.deleteById(id);
    }

    public Page<Book> findPublishersBooks(int id, int pageNumber, int rowPerPage, String sortBy, Sort.Direction direction) {
        return publisherBookCommonService.getPublishersBooks(id, pageNumber, rowPerPage, sortBy, direction);
    }
}

