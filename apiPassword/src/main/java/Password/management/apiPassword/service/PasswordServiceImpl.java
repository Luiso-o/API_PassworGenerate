package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.helper.PasswordMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordMethods passwordMethods;

    @Override
    public List<PasswordDto> generatePasswords(int length, int quantity) {
        List<String> myPasswords;
        List<PasswordDto> passwordDtos = new ArrayList<>();

        myPasswords = IntStream.range(0, quantity)
                .mapToObj(i -> passwordMethods.generaPassword(length))
                .collect(Collectors.toList());

        myPasswords.forEach(myPassword -> {
            boolean secure = passwordMethods.isSecure(myPassword);
            passwordDtos.add(PasswordDto.builder()
                    .password(myPassword)
                    .secure(secure ? "Es segura" : "No es tan segura")
                    .build());
        });

        return passwordDtos;
    }



}
