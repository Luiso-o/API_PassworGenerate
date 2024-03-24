package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.enumerations.PasswordUse;
import Password.management.apiPassword.service.PasswordManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("Manager")
public class PasswordManagerController {

    @Autowired
    private PasswordManagerServiceImpl passwordService;

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
