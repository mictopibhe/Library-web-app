package pl.davidduke.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidPublicationYearImpl implements ConstraintValidator<ValidPublicationYear, Integer> {
    static final Integer MIN_YEAR = 1450;
    @Override
    public void initialize(ValidPublicationYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        if (year == null) {
            return false;
        }
        int currentYear = LocalDate.now().getYear();
        return year >= MIN_YEAR && year <= currentYear;
    }
}
