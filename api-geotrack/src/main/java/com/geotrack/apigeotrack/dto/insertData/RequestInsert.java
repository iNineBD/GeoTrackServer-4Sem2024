package com.geotrack.apigeotrack.dto.insertData;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Parameter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestInsert(@JsonAlias("Id") @Parameter(name = "Id") String idBaseCliente,
                            @JsonAlias("CreatedAt") @Parameter(name = "CreatedAt") LocalDateTime dataHora,
                            @JsonAlias("Latitude") @Parameter(name = "Latitude") BigDecimal latitude,
                            @JsonAlias("Longitude") @Parameter(name = "Longitude") BigDecimal longitude,
                            @JsonAlias("FullName") @Parameter(name = "FullName") String nome,
                            @JsonAlias("Code") @Parameter(name = "Code") String idUsuario) {
}