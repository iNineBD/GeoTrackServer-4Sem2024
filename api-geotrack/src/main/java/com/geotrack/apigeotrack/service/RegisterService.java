package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.login.RegisterRequestDTO;
import com.geotrack.apigeotrack.entities.Login;
import com.geotrack.apigeotrack.repositories.LoginRepository;
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

    @Transactional
    public Login register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        // verificar se ja existe email existente
        if (loginRepository.findByEmail(registerRequestDTO.email()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(registerRequestDTO.password());

        Login newRegister = new Login(
                null,
                registerRequestDTO.name().toUpperCase(),
                encryptedPassword,
                registerRequestDTO.email().trim());

        return loginRepository.save(newRegister);
    }
}
