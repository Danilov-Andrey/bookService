package com.nc.bookservice.services;

import com.nc.bookservice.dto.DataPagination;
import com.nc.bookservice.entities.Book;
import com.nc.bookservice.entities.Publisher;
import com.nc.bookservice.exceptions.publisher.PublisherBooksNotFoundException;
import com.nc.bookservice.repos.BookRepo;
import com.nc.bookservice.repos.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherBookCommonService {
    private BookRepo bookRepo;
    private PublisherRepo publisherRepo;

    @Autowired
    public PublisherBookCommonService(BookRepo bookRepo, PublisherRepo publisherRepo){
        this.bookRepo = bookRepo;
        this.publisherRepo= publisherRepo;
    }

    public Publisher findByName(String publisherName){
        Publisher publisher = publisherRepo.findByName(publisherName);
        return publisher;
    }

    public DataPagination<Book> getPublishersBooks(int id, int pageNumber, int rowPerPage) {
        List<Book> books = bookRepo.findByPublisher_Id(id, PageRequest.of(pageNumber - 1, rowPerPage));
        if (books.size() == 0){
            throw new PublisherBooksNotFoundException("Cannot find any books of this publisher");
        }
        List<Book> allBooks = bookRepo.findByPublisher_Id(id);
        int totalPage = (int) Math.ceil((float)allBooks.size()/rowPerPage);
        DataPagination<Book> dataPagination = new DataPagination<>(totalPage, pageNumber, books);
        return dataPagination;
    }

}
