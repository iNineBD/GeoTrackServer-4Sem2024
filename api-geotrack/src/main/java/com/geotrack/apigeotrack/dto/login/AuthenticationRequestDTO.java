package com.geotrack.apigeotrack.dto.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthenticationRequestDTO(@JsonAlias("email") String email, @JsonAlias("password") String password) {
}
