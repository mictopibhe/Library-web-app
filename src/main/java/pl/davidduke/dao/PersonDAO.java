package pl.davidduke.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pl.davidduke.models.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDAO {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, lastname, birthday) VALUES (?, ?, ?)",
                person.getName(), person.getLastname(), person.getBirthday());
    }

    public Person findById(long id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public Optional<Person> findPersonBy(String name, String lastname, LocalDate birthday) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name=? AND lastname=? AND birthday=?",
                new BeanPropertyRowMapper<>(Person.class), name, lastname, birthday).stream().findAny();
    }

    public void update(long id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, lastname=?, birthday=? WHERE id=?",
                person.getName(), person.getLastname(), person.getBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
