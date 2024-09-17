package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("user") int user,
                                  @JsonAlias("device")int device,
                                  @JsonAlias("dataInicio")LocalDateTime dataInicio,
                                  @JsonAlias("dataFim") LocalDateTime dataFim
                                  ) {
}
