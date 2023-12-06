package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.service.PasswordGeneratorServiceImpl;
import Password.management.apiPassword.service.PasswordManagerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Manager")
public class PasswordManagerController {

    @Autowired
    private PasswordManagerServiceImpl passwordService;

    @Operation(summary = "Guarda tu contraseña en la base de datos", description = "Puedes usar el generador de " +
            "contraseñas o crearte una propia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña guardada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @GetMapping(value = "add")
    public ResponseEntity<PasswordDto> createPassword(
            @RequestParam (required = false, defaultValue = "My new password") String name,
            @RequestParam String password
    ){
        PasswordDto passwordDto = passwordService.savePassword(password,name);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }


}
