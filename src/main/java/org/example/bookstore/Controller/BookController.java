package org.example.bookstore.Controller;


import org.example.bookstore.BookDao.Book;
import org.example.bookstore.Service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    // Constructor Injection is best practice
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 1. Get all books
    // GET: http://localhost:8080/api/books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // 2. Get book by ID
    // GET: http://localhost:8080/api/books/1
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. Add a new book
    // POST: http://localhost:8080/api/books
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // 4. Update an existing book
    // PUT: http://localhost:8080/api/books/1
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 5. Delete a book
    // DELETE: http://localhost:8080/api/books/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (isDeleted) {
            return ResponseEntity.ok("Book deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }
}
