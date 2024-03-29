package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;

import java.util.List;

public interface PasswordGeneratorService {
    List<PasswordGeneratorDto> generatePasswords (int length, int quantity);

}
