package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.enumerations.PasswordUse;

import java.util.List;
import java.util.UUID;

public interface PasswordManagerService {
    PasswordDto savePassword(String password, String name, PasswordUse use);
    PasswordDto findPasswordById(UUID idPassword);
    List<PasswordDto> findPasswordByUse(PasswordUse use);
    PasswordDto updatePassword(UUID idPassword, PasswordUse use, String name, String password);
    void deletePassword(UUID idPassword);
    List<PasswordDto>FindAllMyPasswords(UUID idUser);
}
