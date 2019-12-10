package com.nc.bookservice.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Publisher {
    private String name;

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Book> books;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
