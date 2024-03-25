package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.UserDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.User;

import java.util.UUID;

public interface AuthService {
    UserProfileDto savePasswordInUserProfile(User user, String password, String description);
    void register(UserDto request);
    UserProfileDto authenticateUser(String email, String password);
    User findUserById(UUID id_user);
    void updatePasswordList(User user);
}
