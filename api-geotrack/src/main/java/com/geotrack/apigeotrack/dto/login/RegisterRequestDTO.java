package com.geotrack.apigeotrack.dto.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequestDTO(@NotNull @JsonAlias("name") String name,
                                 @NotNull @JsonAlias("email") String email,
                                 @NotNull @JsonAlias("password") String password) {
}
