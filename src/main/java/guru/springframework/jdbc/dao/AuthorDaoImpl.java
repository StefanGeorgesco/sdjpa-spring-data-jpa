package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.AuthorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jt on 8/28/21.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
        return authorRepository.findByLastName(lastName, pageable).getContent();
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}