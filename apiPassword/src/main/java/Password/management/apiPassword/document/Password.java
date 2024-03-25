package Password.management.apiPassword.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "passwords")
public class Password {
    @MongoId
    private UUID password_id;
    private LocalDate creationDate;
    private int seniority;
    private String description;
    private String password;
    private int length;
}
