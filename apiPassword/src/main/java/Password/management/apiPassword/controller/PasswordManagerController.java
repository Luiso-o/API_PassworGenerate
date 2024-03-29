package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.User;
import Password.management.apiPassword.service.AuthServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("manager")
public class PasswordManagerController {
    private final AuthServiceImpl authService;

    public PasswordManagerController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("id_user") String idUser,
                               @RequestParam("password") String password,
                               @RequestParam("description") String description,
                               Model model) {

        UUID uuid_user = UUID.fromString(idUser);
        User user = authService.findUserById(uuid_user);
        UserProfileDto userProfile = authService.savePasswordInUserProfile(user, password, description);
        model.addAttribute("userProfile", userProfile);
        return "users/account";
    }

    @GetMapping("/account")
    public String showUserAccount(@RequestParam("id_user") String idUser, Model model){
        UUID uuid_user = UUID.fromString(idUser);
        User user = authService.findUserById(uuid_user);
        UserProfileDto userProfile = authService.updatePasswordDetails(user);
        model.addAttribute("userProfile", userProfile);
        return "users/account";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("id_user") UUID idUser,
                                 @RequestParam("id_password") UUID idPassword,
                                 @RequestParam("password") String newPassword,
                                 @RequestParam("description") String newDescription) {
        // Encontrar el usuario por ID
        User user = authService.findUserById(idUser);

        // Encontrar la contraseña específica en la lista de contraseñas del usuario
        Password passwordToUpdate = user.getMyPasswords().stream()
                .filter(password -> password.getPassword_id().equals(idPassword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Contraseña no encontrada"));

        // Actualizar la contraseña y la descripción
        passwordToUpdate.setPassword(newPassword); // Asegúrate de encriptar la contraseña según tus necesidades
        passwordToUpdate.setDescription(newDescription);

        // Guardar el usuario actualizado en la base de datos
        authService.updatePasswordList(user);

        // Redirigir al usuario a la página de perfil o a otra página de éxito
        return "redirect:/user/profile"; // Asegúrate de cambiar la URL a donde necesites redirigir al usuario
    }


}
