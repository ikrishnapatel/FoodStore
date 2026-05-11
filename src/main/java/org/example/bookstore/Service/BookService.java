package org.example.bookstore.Service;

import org.example.bookstore.BookDao.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    // This acts as your temporary in-memory database
    private final List<Book> bookList = new ArrayList<>();

    // Constructor to load some dummy data when the application starts
    public BookService() {
        bookList.add(new Book(1, "Spring Boot in Action", 450.00));
        bookList.add(new Book(2, "Mastering LLD", 550.50));
        bookList.add(new Book(3, "Java 8 Streams", 300.00));
    }

    // GET ALL
    public List<Book> getAllBooks() {
        return bookList;
    }

    // GET BY ID
    public Optional<Book> getBookById(int id) {
        return bookList.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    // CREATE
    public Book addBook(Book book) {
        // Generate a simple mock auto-increment ID
        int newId = bookList.stream().mapToInt(Book::getId).max().orElse(0) + 1;
        book.setId(newId);
        bookList.add(book);
        return book;
    }

    // UPDATE
    public Book updateBook(int id, Book updatedBook) {
        for (int i = 0; i < bookList.size(); i++) {
            Book currentBook = bookList.get(i);
            if (currentBook.getId() == id) {
                // Update the fields
                currentBook.setName(updatedBook.getName());
                currentBook.setPrice(updatedBook.getPrice());
                return currentBook;
            }
        }
        return null; // Return null if the book wasn't found
    }

    // DELETE
    public boolean deleteBook(int id) {
        return bookList.removeIf(book -> book.getId() == id);
    }
}
