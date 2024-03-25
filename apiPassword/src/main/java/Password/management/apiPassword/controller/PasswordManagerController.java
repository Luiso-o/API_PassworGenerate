package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.Dto.UserProfileDto;
import Password.management.apiPassword.document.User;
import Password.management.apiPassword.enumerations.PasswordUse;
import Password.management.apiPassword.service.AuthServiceImpl;
import Password.management.apiPassword.service.PasswordManagerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("manager")
public class PasswordManagerController {

    private final PasswordManagerServiceImpl passwordService;
    private final AuthServiceImpl authService;

    public PasswordManagerController(PasswordManagerServiceImpl passwordService, AuthServiceImpl authService) {
        this.passwordService = passwordService;
        this.authService = authService;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("id_user") String idUser,
                               @RequestParam("password") String password,
                               @RequestParam("description") String description,
                               Model model) {

        UUID uuid_user = UUID.fromString(idUser);
        User user = authService.findUserById(uuid_user);
        authService.savePasswordInUserProfile(user, password, description);

        UserProfileDto userProfile = new UserProfileDto();
        userProfile.setId_user(uuid_user);
        userProfile.setName(user.getName());

        model.addAttribute("userProfile", userProfile);
        return "users/profile";
    }



    //revisar
    @PostMapping(value = "add")
    public ResponseEntity<PasswordDto> createPassword(
            @RequestParam (required = false, defaultValue = "My new password") String name,
            @RequestParam String password,
            @RequestParam PasswordUse use
    ){
        PasswordDto passwordDto = passwordService.savePassword(password,name,use);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @GetMapping(value = "search/id")
    public ResponseEntity<PasswordDto> searchById(
            @RequestParam UUID idPassword
    ){
        PasswordDto passwordDto = passwordService.findPasswordById(idPassword);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @GetMapping(value = "search/use")
    public ResponseEntity<List<PasswordDto>> searchByUse(
            @RequestParam PasswordUse use
    ) {
        List<PasswordDto> passwordDto = passwordService.findPasswordByUse(use);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @PutMapping(value = "update")
    public ResponseEntity<PasswordDto> updatePassword(
            @RequestParam UUID idPassword,
            @RequestParam PasswordUse use,
            @RequestParam (required = false, defaultValue = "My new password") String name,
            @RequestParam (required = false) String password
    ){
        PasswordDto passwordDto = passwordService.updatePassword(idPassword,use,name,password);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Void>deletePassword(@RequestParam UUID idPassword){
        passwordService.deletePassword(idPassword);
        return ResponseEntity.ok().build();
    }

}
