package com.geotrack.apigeotrack.dto.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ValidateRegisterDTO(String name,
                                  String email,
                                  String password) {
}
