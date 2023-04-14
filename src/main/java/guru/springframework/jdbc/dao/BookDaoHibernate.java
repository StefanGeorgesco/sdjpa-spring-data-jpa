package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BookDaoHibernate implements BookDao {
    private final EntityManagerFactory emf;

    public BookDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Book> findAllBooksSort(Pageable pageable) {
        EntityManager em = getEntityManager();
        List<Book> books;
        StringBuilder sb = new StringBuilder();
        sb.append("select b from Book b ");

        if (pageable.getSort().isSorted()) {
            sb
                    .append(" order by ")
                    .append(pageable.getSort().toString().replace(":", ""));
        }

        try {
            TypedQuery<Book> query = em.createQuery(sb.toString(), Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            books = query.getResultList();
        } finally {
            em.close();
        }

        return books;
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        EntityManager em = getEntityManager();
        List<Book> books;

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            books = query.getResultList();
        } finally {
            em.close();
        }

        return books;
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        EntityManager em = getEntityManager();
        List<Book> books;

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setFirstResult(Math.toIntExact(offset));
            query.setMaxResults(pageSize);
            books = query.getResultList();
        } finally {
            em.close();
        }

        return books;
    }

    @Override
    public List<Book> findAllBooks() {
        EntityManager em = getEntityManager();
        List<Book> books;

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            books = query.getResultList();
        } finally {
            em.close();
        }

        return books;
    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book;
        try {
            book = getEntityManager().find(Book.class, id);
        } finally {
            em.close();
        }

        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager em = getEntityManager();
        Book book;

        try {
            Query query = em.createNativeQuery("SELECT * FROM book WHERE title = :title", Book.class);
            query.setParameter("title", title);
            book = (Book) query.getSingleResult();
        } finally {
            em.close();
        }

        return book;
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            em.persist(book);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        Book savedBook = null;
        em.getTransaction().begin();

        try {
            em.merge(book);
            em.flush();
            em.clear();
            savedBook = em.find(Book.class, book.getId());
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return savedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            Book book = em.find(Book.class, id);
            em.remove(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
