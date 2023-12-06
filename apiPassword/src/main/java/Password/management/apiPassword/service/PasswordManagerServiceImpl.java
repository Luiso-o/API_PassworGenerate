package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.helper.PasswordManagerMethods;
import Password.management.apiPassword.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {

    private static final Logger log = LoggerFactory.getLogger(PasswordManagerServiceImpl.class);
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private PasswordManagerMethods passwordMethods;

    @Override
    public PasswordDto savePassword(String password, String name) {
        log.info("Creando documento Password");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        PasswordDocument passwordDocument = PasswordDocument.builder()
                .password_id(UUID.randomUUID())
                .creationDate(formattedDate)
                .length(password.length())
                .name(name)
                .password(password)
                .build();

        log.info("Se a creado el documento correctamente {} ", passwordDocument);

        passwordRepository.save(passwordDocument);
        log.info("Se a guardado el documento en la base de datos correctamente");

        return passwordMethods.convertPasswordDocumentToDto(passwordDocument);
    }
}
