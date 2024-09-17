package com.geotrack.apigeotrack.dto.StopPoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("user") int user,
                                  @JsonAlias("device")int device
//                                  @JsonAlias("dataInicio")LocalDate dataInicio,
//                                  @JsonAlias("dataFim")LocalDate dataFim
                                  ) {
}
