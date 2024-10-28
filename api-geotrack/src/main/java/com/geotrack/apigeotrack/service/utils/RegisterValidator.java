package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.dto.utils.ValidateRegisterDTO;
import org.springframework.stereotype.Component;

@Component
public class RegisterValidator {

    public void validateAll(ValidateRegisterDTO validateRegisterDTO) {
        validateName(validateRegisterDTO.name());

        if (validateRegisterDTO.email() != null) {
            if (!validateEmail(validateRegisterDTO.email())) {
                throw new IllegalArgumentException("Email inválido!");
            }
        }

        validatePassword(validateRegisterDTO.password());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio!");
        }
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres!");
        }
    }
}
