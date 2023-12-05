package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.helper.PasswordGeneratorMethods;
import Password.management.apiPassword.repositories.PasswordGeneratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {
    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorServiceImpl.class);
    @Autowired
    private PasswordGeneratorMethods passwordMethods;
    @Autowired
    private PasswordGeneratorRepository passwordGeneratorRepository;

    @Override
    public List<PasswordGeneratorDto> generatePasswords(int length, int quantity) {
        List<String> myPasswords;
        List<PasswordGeneratorDto> passwordDtos = new ArrayList<>();

        log.info("Generando " + quantity + " contraseñas");
        myPasswords = IntStream.range(0, quantity)
                .mapToObj(i -> passwordMethods.generatePassword(length))
                .collect(Collectors.toList());

        log.info( myPasswords.size() + " contraseñas generadas.");

        myPasswords.forEach(myPassword -> {
            boolean secure = passwordMethods.isSecure(myPassword);
            passwordDtos.add(PasswordGeneratorDto.builder()
                    .password(myPassword)
                    .secure(secure ? "Es segura" : "No es tan segura")
                    .build());
        });
        log.info("Se han convertido los documentos en Dtos");
        return passwordDtos;
    }

    @Override
    public PasswordGeneratorDto savePassword(String password, String name) {
        log.info("Creando documento Password");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = LocalDate.now().format(formatter);

        PasswordDocument passwordDocument = PasswordDocument.builder()
                .creationDate(formattedDate)
                .length(password.length())
                .name(name)
                .password(password)
                .build();

        log.info("Se a creado el documento correctamente {} ", passwordDocument);

        passwordGeneratorRepository.save(passwordDocument);
        log.info("Se a guardado el documento en la base de datos correctamente");

        return passwordMethods.convertPasswordDocumentToDto(passwordDocument);
    }


}
