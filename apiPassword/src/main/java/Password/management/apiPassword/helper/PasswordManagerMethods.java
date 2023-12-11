package Password.management.apiPassword.helper;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class PasswordManagerMethods {

    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorMethods.class);
    @Autowired
    private PasswordGeneratorMethods passwordGeneratorMethods;

    public PasswordDto convertPasswordDocumentToDto(PasswordDocument passwordDocument){
        log.info("Convirtiendo el documento a Dto: {} ", passwordDocument);

        PasswordDto passwordDto = PasswordDto.builder()
                .password_id("ID password: " + passwordDocument.getPassword_id())
                .password("Password: " + passwordDocument.getPassword())
                .creationDate("Días desde la creación de la contraseña: " + passwordDocument.getSeniority() + " días")
                .length("Longitud de la contraseña: " + passwordDocument.getLength())
                .build();

        log.info("La conversión fue exitosa: {} ", passwordDto);
        return passwordDto;
    }

    public int getDaysSinceCreation(LocalDate localDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(localDate, currentDate);
        return Math.abs(period.getDays());
    }


}
