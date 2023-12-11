package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.enumerations.PasswordUse;
import Password.management.apiPassword.exception.UUIDNotFoundException;
import Password.management.apiPassword.helper.PasswordManagerMethods;
import Password.management.apiPassword.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {

    private static final Logger log = LoggerFactory.getLogger(PasswordManagerServiceImpl.class);
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private PasswordManagerMethods passwordMethods;

    @Override
    public PasswordDto savePassword(String password, String name, PasswordUse use) {
        log.info("Creando documento Password");

        PasswordDocument passwordDocument = PasswordDocument.builder()
                .password_id(UUID.randomUUID())
                .creationDate(LocalDate.now())
                .seniority(1)
                .use(use)
                .name(name)
                .password(password)
                .length(password.length())
                .build();

        log.info("Se a creado el documento correctamente {} ", passwordDocument);

        passwordRepository.save(passwordDocument);
        log.info("Se a guardado el documento en la base de datos correctamente");

        return passwordMethods.convertPasswordDocumentToDto(passwordDocument);
    }

    @Override
    public PasswordDto findPasswordById(UUID idPassword) {
        log.info("Buscando la contraseña con el ID: " + idPassword);
        PasswordDocument myPassword = passwordRepository.findById(idPassword)
                .orElseThrow(() -> new UUIDNotFoundException("No se encontró ninguna contraseña con el id " + idPassword));

        passwordMethods.getDaysSinceCreation(myPassword.getCreationDate());
        passwordRepository.save(myPassword);

        return passwordMethods.convertPasswordDocumentToDto(myPassword);
    }

    @Override
    public List<PasswordDto> findPasswordByUse(PasswordUse use) {
        List<PasswordDocument> passwords = passwordRepository.findByUse(use);

        //getDaysSinceCreation: calcula los días transcurridos desde la creacion de la contraseña
        return passwords.stream()
                .peek(password -> passwordMethods.getDaysSinceCreation(password.getCreationDate()))
                .map(passwordMethods::convertPasswordDocumentToDto)
                .collect(Collectors.toList());
    }


}
