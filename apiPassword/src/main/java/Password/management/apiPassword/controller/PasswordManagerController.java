package Password.management.apiPassword.controller;

import Password.management.apiPassword.Dto.PasswordDto;
import Password.management.apiPassword.document.PasswordDocument;
import Password.management.apiPassword.enumerations.PasswordUse;
import Password.management.apiPassword.service.PasswordManagerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Guarda tu contraseña en la base de datos", description = "Puedes usar el generador de " +
            "contraseñas o crearte una propia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña guardada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @PostMapping(value = "add")
    public ResponseEntity<PasswordDto> createPassword(
            @RequestParam (required = false, defaultValue = "My new password") String name,
            @RequestParam String password,
            @RequestParam PasswordUse use
    ){
        PasswordDto passwordDto = passwordService.savePassword(password,name,use);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @Operation(summary = "Busca una contraseña por su id", description = "Busca una contraseña si conoces su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña encontrada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @GetMapping(value = "search/id")
    public ResponseEntity<PasswordDto> searchById(
            @RequestParam UUID idPassword
    ){
        PasswordDto passwordDto = passwordService.findPasswordById(idPassword);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @Operation(summary = "Busca una contraseña por su uso", description = "Busca una contraseña si conoces para que se usa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña encontrada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
    @GetMapping(value = "search/use")
    public ResponseEntity<List<PasswordDto>> searchByUse(
            @RequestParam PasswordUse use
    ) {
        List<PasswordDto> passwordDto = passwordService.findPasswordByUse(use);
        return ResponseEntity.status(HttpStatus.OK).body(passwordDto);
    }

    @Operation(summary = "Actualiza datos de tu contraseña", description = "La fecha de creación se actualizará solo si modificas la contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos actualizados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, Revise response status 500")
    })
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

}
