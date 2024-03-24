package Password.management.apiPassword.helper;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import org.slf4j.Logger;

@Component
public class PasswordGeneratorMethods {

    private static final Logger log = LoggerFactory.getLogger(PasswordGeneratorMethods.class);
    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
    private static final int MIN_NUMBERS = 5;
    private static final int MIN_LOWERCASE = 1;
    private static final int MIN_UPPERCASE = 2;

    public String generatePassword(int length) {
        if (length < MIN_NUMBERS + MIN_LOWERCASE + MIN_UPPERCASE) {
            throw new IllegalArgumentException("La longitud debe ser al menos " + (MIN_NUMBERS + MIN_LOWERCASE + MIN_UPPERCASE) + " para cumplir con los requisitos mínimos.");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        // Asegurar requisitos mínimos
        for (int i = 0; i < MIN_LOWERCASE; i++) {
            password.append(LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length())));
        }
        for (int i = 0; i < MIN_UPPERCASE; i++) {
            password.append(UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length())));
        }
        for (int i = 0; i < MIN_NUMBERS; i++) {
            password.append(NUMBER_CHARS.charAt(random.nextInt(NUMBER_CHARS.length())));
        }

        // Completar el resto de la contraseña
        for (int i = password.length(); i < length; i++) {
            int choice = random.nextInt(4);
            switch (choice) {
                case 0:
                    password.append(LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length())));
                    break;
                case 1:
                    password.append(UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length())));
                    break;
                case 2:
                    password.append(NUMBER_CHARS.charAt(random.nextInt(NUMBER_CHARS.length())));
                    break;
                case 3:
                    password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));
                    break;
            }
        }

        log.info("Contraseña generada: {}", password);
        return password.toString();
    }

    public String classifyPassword(String password) {
        int countNumbers = 0;
        int countLowercase = 0;
        int countUppercase = 0;
        int specialChars = 0;

        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (currentChar >= 'a' && currentChar <= 'z') {
                countLowercase++;
            } else if (currentChar >= 'A' && currentChar <= 'Z') {
                countUppercase++;
            } else if (currentChar >= '0' && currentChar <= '9') {
                countNumbers++;
            } else {
                specialChars++;
            }
        }

        // Clasificación en base a la cantidad de tipos de caracteres y longitud
        if (countNumbers >= 2 && countLowercase >= 2 && countUppercase >= 2 && specialChars >= 2 && password.length() >= 12) {
            return "⭐⭐⭐⭐⭐"; // Avanzado
        } else if ((countNumbers > 0 && countLowercase > 0 && countUppercase > 0) || password.length() >= 8) {
            return "⭐⭐⭐"; // Intermedio
        } else {
            return "⭐"; // Básico
        }
    }

}
