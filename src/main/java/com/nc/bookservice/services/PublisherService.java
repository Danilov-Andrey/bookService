package com.nc.bookservice.services;

import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepo publisherRepo;

    @Autowired
    private BookService bookService;

    public boolean existsById(int id) { return publisherRepo.existsById(id);}

    public Publisher findById(int id) throws Exception {
        Publisher publisher = publisherRepo.findById(id).orElse(null);
        if (publisher == null){
            throw new Exception("Cannot find publisher with id " + id);
        }
        else return publisher;
    }

    public List<Publisher> findAll(int pageNumber, int rowPerPage){
        List<Publisher> publishers = new ArrayList<>();
        publisherRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(publishers::add);
        return publishers;
    }

    public Publisher save(Publisher newPublisher) {
        return publisherRepo.save(newPublisher);
    }

    public void updatePublisher(int id, Publisher publisher) throws Exception{
        Publisher updatedPublisher = findById(id);
        updatedPublisher.setName(publisher.getName());
        publisherRepo.save(updatedPublisher);
    }

    public void deleteById(int id) throws Exception{
        if(!existsById(id)) {
            throw new Exception("Cannot find publisher with id " + id);
        } else {
            publisherRepo.deleteById(id);
        }
    }

    public List<Book> findPublishersBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        return bookService.publishersBooks(id, pageNumber, rowPerPage);
    }

    public Publisher findByName(String publisherName){
        Publisher publisher = publisherRepo.findByName(publisherName);
        return publisher;
    }
}

