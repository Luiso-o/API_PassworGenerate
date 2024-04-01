package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.UserDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.User;
import Password.management.apiPassword.helper.AuthHelper;
import Password.management.apiPassword.repositories.PasswordRepository;
import Password.management.apiPassword.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthHelper authHelper;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, AuthHelper authHelper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.authHelper = authHelper;
    }

    public void register(UserDto request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("El email ya está en uso");
        }
        User user = authHelper.convertDotToUser(request);
        userRepository.save(user);
    }

    public UserProfileDto authenticateUser(String email, String password) {
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new BadCredentialsException("El correo electrónico y la contraseña no pueden estar vacíos");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Autenticación fallida para el usuario: " + email);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));

        return authHelper.castUserToProfileDto(user);
    }

    public UserProfileDto savePasswordInUserProfile(User user, String password, String description){
        Password passwordSaved = Password.builder()
                .password_id(UUID.randomUUID())
                .creationDate(LocalDate.now())
                .seniority(1)
                .description(description)
                .password(password)
                .length(password.length())
                .build();

        user.getMyPasswords().add(passwordSaved);
        updatePasswordList(user);
        return authHelper.castUserToProfileDto(user);
    }

    public UserProfileDto updatePasswordDetails(User user){
        user.getMyPasswords().forEach(authHelper::updateSeniority);
        userRepository.save(user);
        return authHelper.castUserToProfileDto(user);
    }

    @Override
    public Password findPasswordById(List<Password> passwordList, UUID passwordId) {
        return passwordList.stream()
                .filter(password -> password.getPassword_id().equals(passwordId))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("Password not found"));
    }

    @Transactional
    public UserProfileDto updatePasswordInPasswordList(User user, UUID password_id, String newPassword, String newDescription){
       Password passwordToUpdate = findPasswordById(user.getMyPasswords(), password_id);
        boolean updateNeeded = false;

        if (!passwordToUpdate.getPassword().equals(newPassword)) {
            passwordToUpdate.setPassword(newPassword);
            passwordToUpdate.setCreationDate(LocalDate.now());
            passwordToUpdate.setLength(newPassword.length());
            updateNeeded = true;
        }

        if (!passwordToUpdate.getDescription().equals(newDescription)) {
            passwordToUpdate.setDescription(newDescription);
            updateNeeded = true;
        }

        if (updateNeeded) {
            userRepository.save(user);
        }
       return updatePasswordDetails(user);
    }

    @Override
    public User findUserById(UUID id_user) {
        return userRepository.findById(id_user).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el id: " + id_user));
    }

    @Override
    public void updatePasswordList(User user) {
        userRepository.save(user);
    }


}
