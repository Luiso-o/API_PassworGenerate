package Password.management.apiPassword.Dto;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDto {
    private UUID password_id;
    private int seniority;
    private String description;
    private String password;
    private int length;
}
