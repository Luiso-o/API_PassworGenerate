package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.UserDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.User;

import java.util.List;
import java.util.UUID;

public interface AuthService {
    UserProfileDto savePasswordInUserProfile(User user, String password, String description);
    void register(UserDto request);
    UserProfileDto authenticateUser(String email, String password);
    User findUserById(UUID id_user);
    void updatePasswordList(User user);
    UserProfileDto updatePasswordDetails(User user);
    Password findPasswordById(List<Password> passwordList, UUID passwordId);
    UserProfileDto updatePasswordInPasswordList(User user, UUID password_id, String newPassword, String newDescription);
}
