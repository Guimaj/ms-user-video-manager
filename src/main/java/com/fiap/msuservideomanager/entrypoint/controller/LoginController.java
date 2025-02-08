package com.fiap.msuservideomanager.entrypoint.controller;

import com.fiap.msuservideomanager.application.usecase.CriaUsuarioUseCase;
import com.fiap.msuservideomanager.application.usecase.LoginUsuarioUseCase;
import com.fiap.msuservideomanager.domain.model.Error;
import com.fiap.msuservideomanager.domain.model.Login;
import com.fiap.msuservideomanager.domain.model.Token;
import com.fiap.msuservideomanager.domain.model.Usuario;
import com.fiap.msuservideomanager.entrypoint.dto.LoginDTO;
import com.fiap.msuservideomanager.entrypoint.dto.TokenDTO;
import com.fiap.msuservideomanager.entrypoint.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginUsuarioUseCase loginUsuarioUseCase;
    private final CriaUsuarioUseCase criaUsuarioUseCase;

    public LoginController(LoginUsuarioUseCase loginUsuarioUseCase, CriaUsuarioUseCase criaUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.criaUsuarioUseCase = criaUsuarioUseCase;
    }

    @Operation(
            description = "Realiza o cadastro de um usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso!"),
                    @ApiResponse(responseCode = "400",
                            description = "Usuario já posssui cadastro!",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            }
    )
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastro(@RequestBody @Valid LoginDTO loginDTO) {
        Usuario usuario = criaUsuarioUseCase.criarUsuario(new Login(loginDTO.getEmail(), loginDTO.getSenha()));
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(new UsuarioDTO(usuario.getId(), usuario.getEmail()));
    }

    @Operation(
            description = "Realiza o login do cliente e retorna um token jwt autenticado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login feito com sucesso!"),
                    @ApiResponse(responseCode = "403",
                            description = "Email ou senha invalidos!",
                            content = @Content(schema = @Schema(implementation = Error.class)))

            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        Token token = this.loginUsuarioUseCase.loginUsuario(new Login(loginDTO.getEmail(), loginDTO.getSenha()));
        return ResponseEntity.ok(new TokenDTO(token.getJwt()));
    }
}
