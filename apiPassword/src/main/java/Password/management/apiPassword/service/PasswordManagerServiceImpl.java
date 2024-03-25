package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.document.User;
import Password.management.apiPassword.enumerations.PasswordUse;
import Password.management.apiPassword.exception.UUIDNotFoundException;
import Password.management.apiPassword.helper.PasswordManagerMethods;
import Password.management.apiPassword.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordManagerServiceImpl implements PasswordManagerService {
    private static final Logger log = LoggerFactory.getLogger(PasswordManagerServiceImpl.class);
    private final PasswordRepository passwordRepository;
    private final PasswordManagerMethods passwordMethods;


    public PasswordManagerServiceImpl(PasswordRepository passwordRepository, PasswordManagerMethods passwordMethods) {
        this.passwordRepository = passwordRepository;
        this.passwordMethods = passwordMethods;
    }

    //revisar
    @Override
    public PasswordDto savePassword(String password, String name, PasswordUse use) {
        log.info("Creando documento Password");

        PasswordDocument passwordDocument = PasswordDocument.builder()
                .password_id(UUID.randomUUID())
                .creationDate(LocalDate.now())
                .seniority(0)
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
        PasswordDocument myPassword = sharePasswordById(idPassword);
        LocalDate creationDate = myPassword.getCreationDate();

        if (!(creationDate.equals(LocalDate.now()))) {
            passwordMethods.updateSeniority(myPassword);
        }
        return passwordMethods.convertPasswordDocumentToDto(myPassword);
    }

    @Override
    public List<PasswordDto> findPasswordByUse(PasswordUse use) {
        List<PasswordDocument> passwords = passwordRepository.findByUse(use);
        List<PasswordDto> passwordsDto = new ArrayList<>();

        log.info("Tamaño de la lista de contraseñas encontradas: {}", passwords.size());

        if (!(passwords.isEmpty())) {
            for (PasswordDocument password : passwords) {
                LocalDate creationDate = password.getCreationDate();

                if (!(creationDate.equals(LocalDate.now()))) {
                    passwordMethods.updateSeniority(password);

                }
                PasswordDto dto = passwordMethods.convertPasswordDocumentToDto(password);
                passwordsDto.add(dto);
            }
        } else {
            log.info("La lista de contraseñas está vacía.");
        }
        return passwordsDto;
    }

    @Override
    public PasswordDto updatePassword(UUID idPassword, PasswordUse use, String name, String password) {
        PasswordDocument myPassword = sharePasswordById(idPassword);

        if (password == null) {
            password = myPassword.getPassword();
        }

        if (!password.equals(myPassword.getPassword())) {
            myPassword.setPassword(password);
            myPassword.setSeniority(0);
            myPassword.setCreationDate(LocalDate.now());
        }

        myPassword.setUse(use);
        myPassword.setName(name);

        passwordRepository.save(myPassword);
        return passwordMethods.convertPasswordDocumentToDto(myPassword);
    }

    @Override
    public void deletePassword(UUID idPassword) {
        PasswordDocument password = sharePasswordById(idPassword);
        passwordRepository.delete(password);
    }

    @Override
    public List<PasswordDto> FindAllMyPasswords(UUID idUser) {
        return null;
    }

    private PasswordDocument sharePasswordById(UUID idPassword) {
        log.info("Buscando la contraseña con el ID: " + idPassword);
        return passwordRepository.findById(idPassword)
                .orElseThrow(() -> new UUIDNotFoundException("No se encontró ninguna contraseña con el id " + idPassword));

    }
}
