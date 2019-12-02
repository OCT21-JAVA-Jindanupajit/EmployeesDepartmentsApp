package com.jindanupajit.starter.model;

import javax.persistence.*;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
 //   @JoinColumn( name = "book_id")

    private Book book;

    public Store() {
    }

    public Store(String name) {
        this.name = name;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
