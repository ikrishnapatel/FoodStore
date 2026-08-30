package org.example.bookstore;

import org.example.bookstore.BookDao.Book;
import org.example.bookstore.BookDao.Author;
import org.example.bookstore.Repository.BookRepository;
import org.example.bookstore.Repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DataLoader(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorRepository.count() == 0) {
            Author author1 = new Author(0, "Craig Walls", null);
            Author author2 = new Author(0, "Joshua Bloch", null);
            Author author3 = new Author(0, "Robert C. Martin", null);
            Author author4 = new Author(0, "J.R.R. Tolkien", null);
            Author author5 = new Author(0, "George R.R. Martin", null);
            authorRepository.saveAll(List.of(author1, author2, author3, author4, author5));
            System.out.println("Loaded Initial Author Data!");
        }

        if (bookRepository.count() == 0) {
            List<Author> authors = authorRepository.findAll();
            
            Book b1 = new Book(0, "Spring Boot in Action", BigDecimal.valueOf(450.00), "https://m.media-amazon.com/images/I/41-qf8Z11cL._SY445_SX342_.jpg");
            b1.setAuthor(authors.get(0));

            Book b2 = new Book(0, "Effective Java", BigDecimal.valueOf(550.50), "https://m.media-amazon.com/images/I/41T-jN9t2oL._SY445_SX342_.jpg");
            b2.setAuthor(authors.get(1));

            Book b3 = new Book(0, "Clean Code", BigDecimal.valueOf(400.00), "https://m.media-amazon.com/images/I/41xShlnTZTL._SX376_BO1,204,203,200_.jpg");
            b3.setAuthor(authors.get(2));

            Book b4 = new Book(0, "The Clean Coder", BigDecimal.valueOf(350.00), "https://m.media-amazon.com/images/I/41T-jN9t2oL._SY445_SX342_.jpg"); // Placeholder image
            b4.setAuthor(authors.get(2));

            Book b5 = new Book(0, "The Fellowship of the Ring", BigDecimal.valueOf(600.00), "https://m.media-amazon.com/images/I/41bQ1sX+n5L._SY445_SX342_.jpg");
            b5.setAuthor(authors.get(3));

            Book b6 = new Book(0, "The Two Towers", BigDecimal.valueOf(600.00), "https://m.media-amazon.com/images/I/41bQ1sX+n5L._SY445_SX342_.jpg"); // Placeholder
            b6.setAuthor(authors.get(3));

            Book b7 = new Book(0, "The Return of the King", BigDecimal.valueOf(600.00), "https://m.media-amazon.com/images/I/41bQ1sX+n5L._SY445_SX342_.jpg"); // Placeholder
            b7.setAuthor(authors.get(3));

            Book b8 = new Book(0, "A Game of Thrones", BigDecimal.valueOf(750.00), "https://m.media-amazon.com/images/I/41-qf8Z11cL._SY445_SX342_.jpg"); // Placeholder
            b8.setAuthor(authors.get(4));

            Book b9 = new Book(0, "A Clash of Kings", BigDecimal.valueOf(750.00), "https://m.media-amazon.com/images/I/41-qf8Z11cL._SY445_SX342_.jpg"); // Placeholder
            b9.setAuthor(authors.get(4));

            Book b10 = new Book(0, "A Storm of Swords", BigDecimal.valueOf(800.00), "https://m.media-amazon.com/images/I/41-qf8Z11cL._SY445_SX342_.jpg"); // Placeholder
            b10.setAuthor(authors.get(4));

            bookRepository.saveAll(List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10));
            System.out.println("Loaded 10 Initial Books with Authors!");
        }
    }
}
