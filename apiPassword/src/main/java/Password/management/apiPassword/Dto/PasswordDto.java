package Password.management.apiPassword.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
@Builder
public class PasswordDto {
    private String password_id;
    private String password;
    private String creationDate;
    private String length;
}
