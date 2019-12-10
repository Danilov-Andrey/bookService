package com.nc.bookservice.exceptions.publisher;

public class PublisherBooksNotFoundException extends RuntimeException {
    public PublisherBooksNotFoundException(String message){
        super(message);
    }
}
