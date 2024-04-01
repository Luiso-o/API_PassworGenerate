package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.Password;
import Password.management.apiPassword.document.User;
import Password.management.apiPassword.service.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/editPassword/{passwordId}")
    public String editPasswordForm(@PathVariable("passwordId") UUID passwordId, HttpSession session, Model model) {
        UserProfileDto userProfile = (UserProfileDto) session.getAttribute("userProfile");

        if (userProfile == null) {
            return "redirect:users/login";
        }

        try {
            Password password = authService.findPasswordById(userProfile.getMyPasswords(), passwordId);
            model.addAttribute("password", password);
            model.addAttribute("userProfile", userProfile);
        } catch (Exception e) {
            return "redirect:/errorPage";
        }

        return "users/editPassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("id_user") UUID idUser,
                                 @RequestParam("id_password") UUID idPassword,
                                 @RequestParam("password") String newPassword,
                                 @RequestParam("description") String newDescription,
                                 Model model) {

        User user = authService.findUserById(idUser);
        UserProfileDto userProfile = authService.updatePasswordInPasswordList(user,idPassword, newPassword, newDescription);
        model.addAttribute("userProfile", userProfile);

        return "users/account";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id_user") UUID idUser,
                                @RequestParam("id_password") UUID idPassword,
                                Model model){
        User user = authService.findUserById(idUser);
        UserProfileDto userProfile = authService.deletePassword(user, idPassword);
        model.addAttribute("userProfile", userProfile);
        return "users/account";
    }

}
