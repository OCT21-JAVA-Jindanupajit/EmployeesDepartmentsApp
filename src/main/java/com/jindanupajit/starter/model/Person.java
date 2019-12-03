package com.jindanupajit.starter.model;

import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@ActionMapping(
        Action={ActionType.PERSIST, ActionType.MERGE},
        Url = "/person/process",
        Method=RequestMethod.POST)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    @UserInput(Ordinal = 1, Label = "Person Name", PlaceHolder = "Name")
    private String name;

    @UserInput(Ordinal = 2, Label = "Home Address", PlaceHolder = "Address")
    private String address;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn( name = "book_id")
    @UserInput(Ordinal = 3, Label = "Book", PlaceHolder = "Book")
    private Book book;

    public Person() {
    }

    public Person(String name) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
