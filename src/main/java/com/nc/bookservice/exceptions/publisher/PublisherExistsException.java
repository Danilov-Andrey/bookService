package com.nc.bookservice.exceptions.publisher;

public class PublisherExistsException extends RuntimeException {
    public PublisherExistsException(String message){
        super(message);
    }
}
