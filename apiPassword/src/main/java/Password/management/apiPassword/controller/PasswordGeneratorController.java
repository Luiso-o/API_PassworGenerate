package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.service.PasswordGeneratorServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "password")
public class PasswordGeneratorController {

    private final PasswordGeneratorServiceImpl passwordService;

    @Autowired
    public PasswordGeneratorController(PasswordGeneratorServiceImpl passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        int length = 0;
        int quantity = 0;
        model.addAttribute("length", length);
        model.addAttribute("quantity", quantity);
        return "users/profile";
    }

    @GetMapping(value = "generate")
    public String generatePasswords(
            @RequestParam (required = false, defaultValue = "8") int length,
            @RequestParam (required = false, defaultValue = "1") int quantity,
            Model model, HttpSession session
    ){

        UserProfileDto userProfile = (UserProfileDto) session.getAttribute("userProfile");
        if (userProfile != null) {
            model.addAttribute("userProfile", userProfile);
        }

        List<PasswordGeneratorDto> myPasswordsDto = passwordService.generatePasswords(length < 8 ? 8 : length,quantity);
        model.addAttribute("passwords", myPasswordsDto);
        return "users/profile";
    }

}
