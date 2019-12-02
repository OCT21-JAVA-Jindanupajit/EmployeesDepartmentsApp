package com.jindanupajit.starter.model;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "book"
    )
    private Person author;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "book"
    )
    private Store store;

    public Book() {
    }

    public Book(String name, Person author, Store store) {
        this.name = name;
        this.author = author;
        this.store = store;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
