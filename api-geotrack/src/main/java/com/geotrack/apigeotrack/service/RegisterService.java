package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.login.RegisterRequestDTO;
import com.geotrack.apigeotrack.dto.utils.ValidateRegisterDTO;
import com.geotrack.apigeotrack.entities.Login;
import com.geotrack.apigeotrack.repositories.LoginRepository;
import com.geotrack.apigeotrack.service.utils.RegisterValidator;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RegisterService {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RegisterValidator registerValidator;

    @Operation(summary = "Registrar Usuário", description = "Registrar um novo usuário no sistema, cadastrando nome, email e senha")
    @Transactional
    public Login register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        ValidateRegisterDTO objectValid = new ValidateRegisterDTO(
                registerRequestDTO.name(),
                registerRequestDTO.email(),
                registerRequestDTO.password()
        );

        registerValidator.validateAll(objectValid);

        // verify if email already exists
        if (loginRepository.findByEmail(registerRequestDTO.email().trim().toUpperCase()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(registerRequestDTO.password());

        Login newRegister = new Login(
                null,
                registerRequestDTO.name().toUpperCase(),
                encryptedPassword,
                registerRequestDTO.email().trim().toUpperCase());

        return loginRepository.save(newRegister);
    }
}
