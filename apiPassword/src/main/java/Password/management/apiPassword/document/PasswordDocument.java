package Password.management.apiPassword.document;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "passwords")
public class PasswordDocument {

    @Id
    private UUID password_id;
    private String creationDate;
    private int length;
    private String name;
    private String password;

}
