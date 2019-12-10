package com.nc.bookservice.exceptions.authors;

public class AuthorBooksNotFoundException extends RuntimeException {
     public AuthorBooksNotFoundException(String message){
        super(message);
    }
}
