package Password.management.apiPassword.document;

import Password.management.apiPassword.enumerations.PasswordUse;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "passwords/deprecated")
public class PasswordDocument {

    //eliminar despues
    @MongoId
    private UUID password_id;
    private LocalDate creationDate;
    private int seniority;
    private PasswordUse use;
    private String name;
    private String password;
    private int length;

}
