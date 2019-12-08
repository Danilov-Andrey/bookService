package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
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

    private PublisherRepo publisherRepo;

    private BookService bookService;

    @Autowired
    public PublisherService(PublisherRepo publisherRepo, BookService bookService){
        this.bookService = bookService;
        this.publisherRepo = publisherRepo;
    }

    public Publisher findById(int id) throws Exception {
        Publisher publisher = publisherRepo.findById(id).orElse(null);
        if (publisher == null){
            throw new Exception("Cannot find publisher with id " + id);
        }
        return publisher;
    }

    public DataPagination<Publisher> findAll(int pageNumber, int rowPerPage){
        List<Publisher> publishers = new ArrayList<>();
        int totalPage = (int) Math.ceil((float)publisherRepo.count()/rowPerPage);
        publisherRepo.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(publishers::add);
        DataPagination dataPagination = new DataPagination(totalPage, pageNumber, publishers);
        return dataPagination;
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
        if(!publisherRepo.existsById(id)) {
            throw new Exception("Cannot find publisher with id " + id);
        }
        publisherRepo.deleteById(id);
    }

    public List<Book> findPublishersBooks(int id, int pageNumber, int rowPerPage) throws Exception {
        return bookService.publishersBooks(id, pageNumber, rowPerPage);
    }

    public Publisher findByName(String publisherName){
        Publisher publisher = publisherRepo.findByName(publisherName);
        return publisher;
    }
}

