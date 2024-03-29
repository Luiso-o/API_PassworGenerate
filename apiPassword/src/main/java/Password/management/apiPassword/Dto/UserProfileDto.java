package Password.management.apiPassword.Dto;

import Password.management.apiPassword.document.Password;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    //testeado
    private UUID id_user;
    private String name;
    private String surname;
    private List<Password> myPasswords;
}

