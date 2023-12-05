package Password.management.apiPassword.helper;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.document.PasswordDocument;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGeneratorMethods {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
    private static final int MIN_NUMBERS = 5;
    private static final int MIN_LOWERCASE = 1;
    private static final int MIN_UPPERCASE = 2;

    public PasswordGeneratorDto convertPasswordDocumentToDto(PasswordDocument passwordDocument){
        return PasswordGeneratorDto.builder()
                .password(passwordDocument.getPassword())
                .secure(isSecure(passwordDocument.getPassword()) ? "Es segura" : "No es tan segura" )
                .build();
    }

    public String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(4); // 0: minúsculas, 1: mayúsculas, 2: números, 3: caracteres especiales

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

        return password.toString();
    }


    public boolean isSecure(String password) {
        int countNumbers = 0;
        int countLowercase = 0;
        int countUppercase = 0;

        for (int i = password.length() - 1; i >= 0; i--) {
            char currentChar = password.charAt(i);

            if (currentChar >= 'a' && currentChar <= 'z') {
                countLowercase++;
            } else if (currentChar >= 'A' && currentChar <= 'Z') {
                countUppercase++;
            } else if (currentChar >= '0' && currentChar <= '9') {
                countNumbers++;
            }
        }

        return countNumbers >= MIN_NUMBERS && countLowercase >= MIN_LOWERCASE && countUppercase >= MIN_UPPERCASE;
    }
}
