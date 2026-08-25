package org.example.bookstore.Service;

import org.example.bookstore.BookDao.Author;
import org.example.bookstore.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // GET ALL
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // GET BY ID
    public Optional<Author> getAuthorById(int id) {
        return authorRepository.findById(id);
    }

    // CREATE
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    // UPDATE
    public Author updateAuthor(int id, Author updatedAuthor) {
        return authorRepository.findById(id).map(existingAuthor -> {
            existingAuthor.setName(updatedAuthor.getName());
            return authorRepository.save(existingAuthor);
        }).orElse(null);
    }

    // DELETE
    public boolean deleteAuthor(int id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
