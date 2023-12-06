package Password.management.apiPassword.helper;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordManagerMethods {

    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorMethods.class);
    @Autowired
    private PasswordGeneratorMethods passwordGeneratorMethods;

    public PasswordDto convertPasswordDocumentToDto(PasswordDocument passwordDocument){
        log.info("Se está convirtiendo el documento a Dto {} ",passwordDocument);
        PasswordDto passwordDto =  PasswordDto.builder()
                .password_id("ID password: " + passwordDocument.getPassword_id())
                .creationDate("Fecha de creación: " + passwordDocument.getCreationDate())
                .length("Longitud de la contraseña: " + passwordDocument.getLength())
                .build();
        log.info("La conversión fue exitosa {} ", passwordDto);
        return passwordDto;
    }


}
