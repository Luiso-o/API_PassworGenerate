package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordGeneratorDto;
import Password.management.apiPassword.service.PasswordGeneratorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "password")
public class PasswordGeneratorController {

    @Autowired
    private PasswordGeneratorServiceImpl passwordService;

    @Operation(summary = "Genera las contraseñas que necesites", description = "La longitud no debe ser menor a 7, de lo" +
            " contrario el valor por defecto será 8")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseñas generadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @GetMapping(value = "generate")
    public ResponseEntity<List<PasswordGeneratorDto>> generatePasswords(
            @RequestParam (required = false, defaultValue = "8") int length,
            @RequestParam (required = false, defaultValue = "1") int quantity
    ){
        List<PasswordGeneratorDto> myPasswordsDto = passwordService.generatePasswords(length < 8 ? 8 : length,quantity);
        return ResponseEntity.status(HttpStatus.OK).body(myPasswordsDto);
    }

    @Operation(summary = "Guarda tu contraseña en la base de datos", description = "Puedes usar el generador de " +
            "contraseñas o crearte una propia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña guardada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @GetMapping(value = "add")
    public ResponseEntity<PasswordGeneratorDto> createPassword(
            @RequestParam String password,
            @RequestParam String name
    ){
        PasswordGeneratorDto passwordGeneratorDto = passwordService.savePassword(password,name);
        return ResponseEntity.status(HttpStatus.OK).body(passwordGeneratorDto);
    }

}
