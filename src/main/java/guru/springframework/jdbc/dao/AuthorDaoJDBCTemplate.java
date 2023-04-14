package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AuthorDaoJDBCTemplate implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from author where last_name = ? ");

        if (pageable.getSort().isSorted()) {
            sb
                    .append("order by ")
                    .append(pageable.getSort().toString().replace(":", ""))
                    .append(" ");
        }

        sb.append("limit ? offset ?");

        return jdbcTemplate.query(sb.toString(), getAuthorMapper(),
                lastName,
                pageable.getPageSize(),
                pageable.getOffset());
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

    private AuthorMapper getAuthorMapper() {
        return new AuthorMapper();
    }
}
