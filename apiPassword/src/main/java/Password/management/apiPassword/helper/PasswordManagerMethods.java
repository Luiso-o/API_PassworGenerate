package Password.management.apiPassword.helper;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class PasswordManagerMethods {

    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorMethods.class);
    @Autowired
    private PasswordGeneratorMethods passwordGeneratorMethods;
    @Autowired
    private PasswordRepository passwordRepository;




}
