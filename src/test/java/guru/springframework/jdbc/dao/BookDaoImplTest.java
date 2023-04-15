package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@Import(BookDaoImpl.class) // @ComponentScan not working
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;

    @Test
    void findAllBooksPage1_Sort() {
        List<Book> books = bookDao.findAllBooksSort(PageRequest.of(0, 10,
                Sort.by(List.of(Sort.Order.desc("title"), Sort.Order.asc("isbn")))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
        assertThat(books.get(0).getTitle()).isEqualTo("Spring in Action, 6th Edition");
        assertThat(books.get(0).getId()).isEqualTo(3L);
    }

    @Test
    void findAllBooksPage1_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPage2_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(1, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPage10_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(9, 10));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void findAllBooksPage1() {
        List<Book> books = bookDao.findAllBooks(10, 0);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPage2() {
        List<Book> books = bookDao.findAllBooks(10, 10);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPage10() {
        List<Book> books = bookDao.findAllBooks(10, 90);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(4);
    }

    @Test
    void getById() {
        Book book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    void findBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void saveNewBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");

        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void deleteBookById() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        assertThrows(NoSuchElementException.class, () -> bookDao.getById(saved.getId()));
    }
}