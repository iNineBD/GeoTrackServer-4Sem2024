package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.login.AuthenticationRequestDTO;
import com.geotrack.apigeotrack.dto.login.LoginResponseDTO;
import com.geotrack.apigeotrack.entities.Login;
import com.geotrack.apigeotrack.repositories.LoginRepository;
import com.geotrack.apigeotrack.service.utils.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Login", description = "Realiza o login do usuário")
    public LoginResponseDTO login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {

        if (authenticationRequestDTO.email().isEmpty() || authenticationRequestDTO.password().isEmpty()) {
            throw new IllegalArgumentException("Email ou senha não podem ser vazios!");
        }

        // verify if email exists
        if (loginRepository.findByEmail(authenticationRequestDTO.email().trim().toUpperCase()) == null) {
            throw new IllegalArgumentException("Email está incorreto ou não possui cadastro");
        }

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    authenticationRequestDTO.email().trim().toUpperCase(),
                    authenticationRequestDTO.password());

            Authentication authentication = authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((Login) authentication.getPrincipal());

            return new LoginResponseDTO(token);

        } catch (BadCredentialsException e) {

            throw new IllegalArgumentException("Senha invalida!");
        }
    }
}
