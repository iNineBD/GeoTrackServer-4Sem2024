package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.login.AuthenticationRequestDTO;
import com.geotrack.apigeotrack.dto.login.LoginResponseDTO;
import com.geotrack.apigeotrack.entities.Login;
import com.geotrack.apigeotrack.service.utils.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {

        if(authenticationRequestDTO.email().isEmpty() || authenticationRequestDTO.password().isEmpty()){
            throw new IllegalArgumentException("Email ou senha não podem ser vazios!");
        }

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    authenticationRequestDTO.email().trim().toUpperCase(),
                    authenticationRequestDTO.password());

            Authentication authentication = authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((Login) authentication.getPrincipal());

            return new LoginResponseDTO(token);

        } catch (BadCredentialsException e) {

            throw new IllegalArgumentException("Login Invalido!");
        }
    }
}
