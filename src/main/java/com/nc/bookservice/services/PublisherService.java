package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.publisher.PublisherExistsException;
import com.nc.bookservice.exceptions.publisher.PublisherNotFoundException;
import com.nc.bookservice.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {
    private PublisherRepo publisherRepo;
    private PublisherBookCommonService publisherBookCommonService;

    @Autowired
    public PublisherService(PublisherBookCommonService publisherBookCommonService, PublisherRepo publisherRepo){
        this.publisherBookCommonService = publisherBookCommonService;
        this.publisherRepo = publisherRepo;
    }

    public Publisher findById(int id) {
        Publisher publisher = publisherRepo.findById(id).orElse(null);
        if (publisher == null){
            throw new PublisherNotFoundException("Cannot find publisher with id " + id);
        }
        return publisher;
    }

    public DataPagination<Publisher> findAll(int pageNumber, int rowPerPage){
        List<Publisher> publishers = new ArrayList<>();
        publisherRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(publishers::add);
        if(publishers.size() == 0) {
            throw new PublisherNotFoundException("Cannot find any publishers");
        }
        int totalPage = (int) Math.ceil((float)publisherRepo.count()/rowPerPage);
        DataPagination<Publisher> dataPagination = new DataPagination(totalPage, pageNumber, publishers);
        return dataPagination;
    }

    public Publisher savePublisher(Publisher newPublisher) {
        Publisher dbPublisher = publisherBookCommonService.findByName(newPublisher.getName());
        if (dbPublisher != null){
            throw new PublisherExistsException("This publisher exists");
        }
        return publisherRepo.save(newPublisher);
    }

    public Publisher updatePublisher(int id, Publisher publisher) {
        Publisher updatedPublisher = findById(id);
        updatedPublisher.setName(publisher.getName());
        return publisherRepo.save(updatedPublisher);
    }

    public void deleteById(int id) {
        if(!publisherRepo.existsById(id)) {
            throw new PublisherNotFoundException("Cannot find publisher with id " + id);
        }
        publisherRepo.deleteById(id);
    }

    public DataPagination<Book> findPublishersBooks(int id, int pageNumber, int rowPerPage) {
        return publisherBookCommonService.getPublishersBooks(id, pageNumber, rowPerPage);
    }
}

