package org.example.bookstore.Service;

import org.example.bookstore.BookDao.Book;
import org.example.bookstore.BookDao.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET ALL
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // GET BY ID
    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    // CREATE
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // UPDATE
    public Book updateBook(int id, Book updatedBook) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setName(updatedBook.getName());
            existingBook.setPrice(updatedBook.getPrice());
            return bookRepository.save(existingBook);
        }).orElse(null);
    }

    // DELETE
    public boolean deleteBook(int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
