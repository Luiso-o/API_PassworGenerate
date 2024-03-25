package Password.management.apiPassword.service;

import Password.management.apiPassword.Dto.UserDto;
import Password.management.apiPassword.Dto.UserProfileDto;

public interface AuthService {
    void register(UserDto request);
    UserProfileDto authenticateUser(String email, String password);
}
