package Password.management.apiPassword.document;

import Password.management.apiPassword.enumerations.PasswordUse;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "passwords")
public class PasswordDocument {

    @MongoId
    private UUID password_id;
    private LocalDate creationDate;
    private int seniority;
    private PasswordUse use;
    private String name;
    private String password;
    private int length;

}
