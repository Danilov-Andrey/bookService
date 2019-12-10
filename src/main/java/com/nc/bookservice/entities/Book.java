package com.nc.bookservice.entities;

import javax.persistence.*;

@Entity
public class Book {
    private String name;
    private int year;

    public Book(){
    }

    public Book(Author author, Publisher publisher, String name, int year, Copies copies) {
        this.author = author;
        this.name = name;
        this.year = year;
        this.publisher = publisher;
        this.copies = copies;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "copies_id" , nullable = false)
    private Copies copies;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Copies getCopies() {
        return copies;
    }

    public void setCopies(Copies copies) {
        this.copies = copies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
