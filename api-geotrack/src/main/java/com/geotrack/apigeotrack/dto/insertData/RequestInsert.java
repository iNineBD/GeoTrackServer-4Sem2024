package com.geotrack.apigeotrack.dto.insertData;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestInsert(@JsonAlias("Id") String idBaseCliente,
                            @JsonAlias("CreatedAt") LocalDateTime dataHora,
                            @JsonAlias("Latitude") BigDecimal latitude,
                            @JsonAlias("Longitude") BigDecimal longitude,
                            @JsonAlias("FullName") String nome,
                            @JsonAlias("Code") String idUsuario)
{
}