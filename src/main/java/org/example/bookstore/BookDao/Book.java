package org.example.bookstore.BookDao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "authors")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Double price;

    private String imageUrl;

    // "mappedBy" tells JPA that the Author class owns the relationship mapping
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    public Book(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Book(int id, String name, Double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
