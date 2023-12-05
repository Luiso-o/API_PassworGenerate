package Password.management.apiPassword.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "passwords")
public class PasswordDocument {

    @MongoId
    private UUID password_id;
    private int length;
    private String name;
    private String password;

}
