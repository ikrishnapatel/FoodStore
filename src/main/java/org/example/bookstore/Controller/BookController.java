package org.example.bookstore.Controller;


import org.example.bookstore.BookDao.Book;
import org.example.bookstore.BookDao.BookCreationRequest;
import org.example.bookstore.BookDao.BulkDeleteResponse;
import org.example.bookstore.Response.ApiResponse;
import org.example.bookstore.Service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET: http://localhost:8080/api/books/health
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Bookstore API is up and running!", null));
    }

    // 1. Get all books
    // GET: http://localhost:8080/api/books
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        return ResponseEntity.ok(ApiResponse.success("Books retrieved successfully", bookService.getAllBooks()));
    }

    // 2. Get book by ID
    // GET: http://localhost:8080/api/books/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success("Book retrieved successfully", book));
    }

    // 3. Add a new book
    // POST: http://localhost:8080/api/books
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody BookCreationRequest request) {
        Book savedBook = bookService.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", savedBook));
    }

    // 4. Update an existing book
    // PUT: http://localhost:8080/api/books/1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable int id, @Valid @RequestBody BookCreationRequest request) {
        Book updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", updatedBook));
    }

    // 5. Delete a book
    // DELETE: http://localhost:8080/api/books/1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }
    // 6. Delete multiple books
    // DELETE: http://localhost:8080/api/books/bulk
    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<BulkDeleteResponse>> deleteBooks(@RequestBody List<Integer> ids) {
        BulkDeleteResponse response = bookService.deleteBooks(ids);
        
        if (response.getDeletedCount() == 0) {
            // None found / all invalid -> 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No books found to delete", response));
        } else if (!response.getNotFoundIds().isEmpty()) {
            // Some deleted, some not found -> 207 Multi-Status
            String message = response.getDeletedCount() + " of " + response.getRequestedCount() + " books deleted successfully";
            return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                    .body(ApiResponse.success(HttpStatus.MULTI_STATUS.value(), message, response));
        } else {
            // All deleted -> 200 OK
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "All books deleted successfully", response));
        }
    }
}
