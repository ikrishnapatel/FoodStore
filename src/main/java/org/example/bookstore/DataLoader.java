package org.example.bookstore;

import org.example.bookstore.BookDao.Book;
import org.example.bookstore.BookDao.BookRepository;
import org.example.bookstore.BookDao.User;
import org.example.bookstore.BookDao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public DataLoader(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            bookRepository.saveAll(List.of(
                    new Book(0, "Spring Boot in Action", 450.00, "https://m.media-amazon.com/images/I/41-qf8Z11cL._SY445_SX342_.jpg"),
                    new Book(0, "Mastering LLD", 550.50, "https://m.media-amazon.com/images/I/41T-jN9t2oL._SY445_SX342_.jpg"),
                    new Book(0, "Java 8 Streams", 300.00, "https://m.media-amazon.com/images/I/41bQ1sX+n5L._SY445_SX342_.jpg")
            ));
            System.out.println("Loaded Initial Book Data!");
        }

        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    new User(null, "admin", "admin123", "admin"),
                    new User(null, "user", "user123", "user")
            ));
            System.out.println("Loaded Initial User Data!");
        }
    }
}
