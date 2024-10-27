package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.dto.utils.ValidateRegisterDTO;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RegisterValidator {

    public static void validatesAll(ValidateRegisterDTO validateRegisterDTO){
        validatesName(validateRegisterDTO.name());
        if(validateRegisterDTO.email() != null){
            if(!validatesEmail(validateRegisterDTO.email())){
                throw new IllegalArgumentException("Email inválido!");
            }
        }
        validatesPassword(validateRegisterDTO.password());
    }

    public static void validatesName(String name){
        if(name == null){
            throw new IllegalArgumentException("Nome não pode ser vazio!");
        }
    }

    public static boolean validatesEmail(String email) {
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    public static void validatesPassword(String password) {
        if(password == null){
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        if (password.length() < 6 ){
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres!");
        }
    }
}
