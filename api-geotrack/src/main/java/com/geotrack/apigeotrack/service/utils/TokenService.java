package com.geotrack.apigeotrack.service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.geotrack.apigeotrack.entities.Login;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Get secret from application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Generate token for user login
    public String generateToken(Login login){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Create token
            String token = JWT.create()
                    .withIssuer("api-geotrack")
                    .withSubject(login.getEmail())
                    // Token expires in 2 hours
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error creating token", exception);
        }
    }

    // Validate token for user login
    public String validadeToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("api-geotrack")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception exception){
            throw new RuntimeException("Error validating token", exception);
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now(ZoneOffset.UTC).plusHours(2).toInstant(ZoneOffset.UTC);
    }


}
