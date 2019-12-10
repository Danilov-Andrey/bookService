package com.nc.bookservice.exceptions;

public class PublisherBooksAreNotFoundException extends RuntimeException {
    public PublisherBooksAreNotFoundException(String message){
        super(message);
    }
}
