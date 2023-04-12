package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow();
    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
