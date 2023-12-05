package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.service.PasswordServiceImpl;
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
public class PasswordController {

    @Autowired
    private PasswordServiceImpl passwordService;

    @Operation(summary = "Genera las contraseñas que necesites", description = "La longitud no debe ser menor a 7, de lo" +
            " contrario el valor por defecto será 8")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseñas generadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @GetMapping(value = "generate")
    public ResponseEntity<List<PasswordDto>> generatePasswords(
            @RequestParam int length,
            @RequestParam int quantity
    ){
        List<PasswordDto> myPasswordsDto = passwordService.generatePasswords(length < 8 ? 8 : length,quantity);
        return ResponseEntity.status(HttpStatus.OK).body(myPasswordsDto);
    }

}
