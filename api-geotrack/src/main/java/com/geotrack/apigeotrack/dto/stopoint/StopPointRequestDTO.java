package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("user") int user,
                                  @JsonAlias("device") Long device,
                                  @JsonAlias("dataInicio") Timestamp dataInicio,
                                  @JsonAlias("dataFim") Timestamp dataFim
) {
}
