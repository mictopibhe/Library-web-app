package pl.davidduke.dao;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.davidduke.models.Book;
import pl.davidduke.models.Person;

import java.util.List;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDAO {
    final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?",
                new BeanPropertyRowMapper<>(Book.class), id);
    }

    public void update(long id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public List<Book> findBooksByPersonId(long personId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new BeanPropertyRowMapper<>(Book.class), personId);
    }

    public Optional<Person> getBookOwner(long id) {
        return jdbcTemplate.query("SELECT p.id, p.name, p.lastname, p.birthday " +
                        "FROM book b JOIN person p ON b.person_id=p.id WHERE b.id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void assign(long id, Person person) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person.getId(), id);
    }

    public void release(long id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", id);
    }
}
