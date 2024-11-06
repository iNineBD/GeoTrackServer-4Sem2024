package com.geotrack.apigeotrack.dto.insertdata;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "RequestInsert", description = "DTO para inserção de dados")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestInsert(@JsonAlias("Id")
                            @Schema(description = "Identificador único da base de clientes", example = "CUST12345")
                            String idCustomerBase,

                            @JsonAlias("CreatedAt")
                            @Schema(description = "Data e hora de criação do registro", example = "2024-01-01T10:00:00")
                            LocalDateTime dateTime,

                            @JsonAlias("Latitude")
                            @Schema(description = "Latitude da localização do cliente", example = "-23.550520")
                            BigDecimal latitude,

                            @JsonAlias("Longitude")
                            @Schema(description = "Longitude da localização do cliente", example = "-46.633308")
                            BigDecimal longitude,

                            @JsonAlias("FullName")
                            @Schema(description = "Nome completo do cliente", example = "João da Silva")
                            String name,

                            @JsonAlias("Code")
                            @Schema(description = "Código de identificação do usuário", example = "USR12345")
                            String idUser) {
}