package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AuthorDaoHibernate implements AuthorDao {
    private final EntityManagerFactory emf;

    public AuthorDaoHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
            EntityManager em = getEntityManager();
        List<Author> authors;
        StringBuilder sb = new StringBuilder();
        sb.append("select a from Author a where a.lastName = :lastName ");

        if (pageable.getSort().isSorted()) {
            sb
                    .append(" order by ")
                    .append(pageable.getSort().toString().replace(":", ""));
        }

        try {
            TypedQuery<Author> query = em.createQuery(sb.toString(), Author.class);
            query.setParameter("lastName", lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            authors = query.getResultList();
        } finally {
            em.close();
        }

        return authors;
    }

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }
    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
