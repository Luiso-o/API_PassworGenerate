package Password.management.apiPassword.helper;


import Password.management.apiPassword.Dto.UserDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class AuthHelper {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convertDotToUser(UserDto user){
        return User.builder()
                .id_user(UUID.randomUUID())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .myPasswords(new ArrayList<>())
                .build();
    }

    public UserProfileDto castUserToProfileDto(User user){
        return UserProfileDto.builder()
                .id_user(user.getId_user())
                .name(user.getName())
                .surname(user.getSurname())
                .myPasswords(user.getMyPasswords())
                .build();
    }

    //modifica la antigüedad de la contrasena
    public void updateSeniority(Password myPassword){
        int updateDay = getDaysSinceCreation(myPassword.getCreationDate());
        myPassword.setSeniority(Math.max(updateDay, 1));
    }

    //calcula los días transcurridos desde la creacion de la contraseña
    public int getDaysSinceCreation(LocalDate localDate) {
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(localDate, currentDate);
    }

}

