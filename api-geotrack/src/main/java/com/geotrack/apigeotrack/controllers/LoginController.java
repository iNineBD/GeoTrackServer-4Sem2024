package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.login.AuthenticationRequestDTO;
import com.geotrack.apigeotrack.dto.login.LoginResponseDTO;
import com.geotrack.apigeotrack.dto.login.RegisterRequestDTO;
import com.geotrack.apigeotrack.service.LoginService;
import com.geotrack.apigeotrack.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Autenticação de Usuários", description = "Operações de Login e Cadastro de Usuários")
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    RegisterService registerService;

    @Operation(summary = "Login", description = "Realiza o login de um usuário, na aplicação, retornando o token de autenticação, usado em requisições futuras")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {

        LoginResponseDTO response = loginService.login(authenticationRequestDTO);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cadastro", description = "Realiza o cadastro de um usuário, na aplicação, salvando-o na base de dados")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        registerService.register(registerRequestDTO);

        return new ResponseEntity<>(Map.of("message", "Usuário cadastrado com sucesso"), HttpStatus.CREATED);

    }
}
