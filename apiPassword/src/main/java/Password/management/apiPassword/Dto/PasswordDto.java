package Password.management.apiPassword.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PasswordDto {
    private String password;
    private String secure;
}
