package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.document.PasswordDocument;

import java.util.List;

public interface PasswordGeneratorService {
    List<PasswordGeneratorDto> generatePasswords (int length, int quantity);

}
