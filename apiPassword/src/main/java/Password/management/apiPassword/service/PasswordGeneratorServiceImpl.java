package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.helper.PasswordGeneratorMethods;
import Password.management.apiPassword.helper.PasswordManagerMethods;
import Password.management.apiPassword.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {
    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorServiceImpl.class);
    @Autowired
    private PasswordGeneratorMethods passwordGeneratorMethods;
    @Autowired
    private PasswordRepository passwordGeneratorRepository;
    @Autowired
    private PasswordManagerMethods passwordMethods;

    @Override
    public List<PasswordGeneratorDto> generatePasswords(int length, int quantity) {
        List<String> myPasswords;
        List<PasswordGeneratorDto> passwordDtos = new ArrayList<>();

        log.info("Generando " + quantity + " contraseñas");
        myPasswords = IntStream.range(0, quantity)
                .mapToObj(i -> passwordGeneratorMethods.generatePassword(length))
                .toList();

        log.info( myPasswords.size() + " contraseñas generadas.");

        myPasswords.forEach(myPassword -> {
            boolean secure = passwordGeneratorMethods.isSecure(myPassword);
            passwordDtos.add(PasswordGeneratorDto.builder()
                    .password(myPassword)
                    .secure(secure ? "Es muy segura" : "Es medianamente segura")
                    .build());
        });
        log.info("Se han convertido los documentos en Dtos");
        return passwordDtos;
    }

}
