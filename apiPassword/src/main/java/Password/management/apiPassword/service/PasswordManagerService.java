package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;

public interface PasswordManagerService {
    PasswordDto savePassword(String password, String name);
}
