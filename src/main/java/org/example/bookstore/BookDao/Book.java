package org.example.bookstore.BookDao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Book {

    private int id;
    private String name;
    private Double price;

    public Book()
    {
    }

}
