package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;

import java.util.List;

public interface PasswordService {
    List<PasswordDto> generatePasswords (int length, int quantity);
}
