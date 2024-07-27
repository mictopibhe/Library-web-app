package pl.davidduke.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.davidduke.validators.ValidPublicationYear;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    int id;
    @NotBlank(message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Book title should be between 2 and 100 characters")
    String title;
    @NotBlank(message = "Author name should not be empty")
    @Size(min = 2, max = 100, message = "Author name should be between 2 and 100 characters")
    String author;
    @ValidPublicationYear(message = "The year must be from 1450 to the present")
    int year;
    int ownerId;
}
