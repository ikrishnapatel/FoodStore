package org.example.bookstore.Service;

import org.example.bookstore.BookDao.Book;
import org.example.bookstore.Repository.BookRepository;
import org.example.bookstore.BookDao.BookCreationRequest;
import org.example.bookstore.Repository.AuthorRepository;
import org.example.bookstore.BookDao.BulkDeleteResponse;
import org.example.bookstore.Exception.BookNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // GET ALL
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // GET BY ID
    public Book getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    // CREATE
    @Transactional
    public Book addBook(BookCreationRequest request) {
        Book newBook = new Book();
        newBook.setName(request.getName());
        newBook.setPrice(request.getPrice());
        newBook.setImageUrl(request.getImageUrl());
        
        if (request.getAuthorId() != null) {
            authorRepository.findById(request.getAuthorId()).ifPresent(newBook::setAuthor);
        }

        return bookRepository.save(newBook);
    }

    // UPDATE
    @Transactional
    public Book updateBook(int id, BookCreationRequest request) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        existingBook.setName(request.getName());
        existingBook.setPrice(request.getPrice());
        existingBook.setImageUrl(request.getImageUrl());
        
        if (request.getAuthorId() != null) {
            authorRepository.findById(request.getAuthorId()).ifPresent(existingBook::setAuthor);
        }

        return bookRepository.save(existingBook);
    }

    // DELETE
    public boolean deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        return true;
    }
    // DELETE MULTIPLE
    @Transactional
    public BulkDeleteResponse deleteBooks(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new BulkDeleteResponse(0, 0, List.of(), List.of());
        }
        
        List<Book> existingBooks = bookRepository.findAllById(ids);
        List<Integer> existingIds = existingBooks.stream().map(Book::getId).collect(Collectors.toList());
        List<Integer> notFoundIds = ids.stream().filter(id -> !existingIds.contains(id)).collect(Collectors.toList());
        
        if (!existingBooks.isEmpty()) {
            bookRepository.deleteAll(existingBooks);
        }
        
        return new BulkDeleteResponse(existingIds.size(), ids.size(), existingIds, notFoundIds);
    }
}
