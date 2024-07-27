package pl.davidduke.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    int id;
    @NotBlank(message = "Name should not be empty")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    String name;
    @NotBlank(message = "Lastname should not be empty")
    @Size(min = 3, max = 20, message = "lastname must be between 3 and 20 characters")
    String lastname;
    @Past(message = "Birthday must be a past date")
    @NotNull(message = "Birthday should not be empty")
    LocalDate birthday;
}
