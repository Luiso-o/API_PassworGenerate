package Password.management.apiPassword.helper;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class PasswordManagerMethods {

    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorMethods.class);
    @Autowired
    private PasswordGeneratorMethods passwordGeneratorMethods;
    @Autowired
    private PasswordRepository passwordRepository;

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

    //modifica la antigüedad de la contrasena
    public void updateSeniority(PasswordDocument myPassword){
        int updateDay = getDaysSinceCreation(myPassword.getCreationDate());
        myPassword.setSeniority(updateDay);
        passwordRepository.save(myPassword);
        log.info("Se ha actualizado la antigüedad de la contraseña");
    }

    //calcula los días transcurridos desde la creacion de la contraseña
    public int getDaysSinceCreation(LocalDate localDate) {
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(localDate, currentDate);
    }


}
