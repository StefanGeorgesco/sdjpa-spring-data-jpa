package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooksSort(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return findAllBooks(PageRequest.of(offset / pageSize, pageSize));
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
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
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}