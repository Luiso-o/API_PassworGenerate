package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.UserDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.service.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final AuthServiceImpl authService;

    @Autowired
    public UserController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        UserDto userInfo = new UserDto();
        model.addAttribute("userInfo", userInfo);
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute UserDto userInfo, RedirectAttributes redirectAttributes){
        authService.register(userInfo);
        redirectAttributes.addFlashAttribute("successMessage", "User successfully register!");
        return "users/login";
    }

    @GetMapping("/login")
    public String authenticate(Model model) {
        String email = "";
        String password = "";
        model.addAttribute("email",email);
        model.addAttribute("password",password);
        return "users/login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        try {
            UserProfileDto userProfile = authService.authenticateUser(email, password);
            session.setAttribute("userProfile", userProfile);
            return "redirect:/users/profile";
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid email or password.");
            return "users/login";
        }
    }

    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        UserProfileDto userProfile = (UserProfileDto) session.getAttribute("userProfile");

        if (userProfile == null) {
            return "redirect:/users/login";
        }

        model.addAttribute("userProfile", userProfile);
        return "users/profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/register";
    }

}