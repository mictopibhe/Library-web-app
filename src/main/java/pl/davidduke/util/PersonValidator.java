package pl.davidduke.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.davidduke.dao.PersonDAO;
import pl.davidduke.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDAO.findPersonBy(person.getName(), person.getLastname(), person.getBirthday()).isPresent()) {
            errors.rejectValue("name", null, "Person already exists");
            errors.rejectValue("lastname", null, "Person already exists");
            errors.rejectValue("birthday", null, "Person already exists");
        }
    }
}
