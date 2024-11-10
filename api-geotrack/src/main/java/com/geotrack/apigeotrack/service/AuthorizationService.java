package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.repositories.LoginRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    @Operation(summary = "Carrega um usuário pelo email", description = "Carrega um usuário pelo email")
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = loginRepository.findByEmail(email);
        if (userDetails == null) {
            throw new UsernameNotFoundException(email);
        }
        return userDetails;
    }
}
