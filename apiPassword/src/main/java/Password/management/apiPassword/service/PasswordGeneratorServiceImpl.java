package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.helper.PasswordGeneratorMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {

    @Autowired
    private PasswordGeneratorMethods passwordMethods;

    @Override
    public List<PasswordGeneratorDto> generatePasswords(int length, int quantity) {
        List<String> myPasswords;
        List<PasswordGeneratorDto> passwordDtos = new ArrayList<>();

        myPasswords = IntStream.range(0, quantity)
                .mapToObj(i -> passwordMethods.generatePassword(length))
                .collect(Collectors.toList());

        myPasswords.forEach(myPassword -> {
            boolean secure = passwordMethods.isSecure(myPassword);
            passwordDtos.add(PasswordGeneratorDto.builder()
                    .password(myPassword)
                    .secure(secure ? "Es segura" : "No es tan segura")
                    .build());
        });

        return passwordDtos;
    }



}
