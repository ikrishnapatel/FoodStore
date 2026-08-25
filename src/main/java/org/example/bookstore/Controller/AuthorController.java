package org.example.bookstore.Controller;

import org.example.bookstore.BookDao.Author;
import org.example.bookstore.Response.ApiResponse;
import org.example.bookstore.Service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // 1. Get all authors
    // GET: http://localhost:8080/api/authors
    @GetMapping
    public ResponseEntity<ApiResponse<List<Author>>> getAllAuthors() {
        return ResponseEntity.ok(ApiResponse.success("Authors retrieved successfully", authorService.getAllAuthors()));
    }

    // 2. Get author by ID
    // GET: http://localhost:8080/api/authors/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Author>> getAuthorById(@PathVariable int id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(a -> ResponseEntity.ok(ApiResponse.success("Author retrieved successfully", a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. Add a new author
    // POST: http://localhost:8080/api/authors
    @PostMapping
    public ResponseEntity<ApiResponse<Author>> addAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = authorService.addAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Author created successfully", savedAuthor));
    }

    // 4. Update an existing author
    // PUT: http://localhost:8080/api/authors/1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Author>> updateAuthor(@PathVariable int id, @Valid @RequestBody Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        if (updatedAuthor != null) {
            return ResponseEntity.ok(ApiResponse.success("Author updated successfully", updatedAuthor));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 5. Delete an author
    // DELETE: http://localhost:8080/api/authors/1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable int id) {
        boolean isDeleted = authorService.deleteAuthor(id);
        if (isDeleted) {
            return ResponseEntity.ok(ApiResponse.success("Author deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
