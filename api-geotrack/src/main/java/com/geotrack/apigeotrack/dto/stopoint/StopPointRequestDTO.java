package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "StopPointRequestDTO", description = "DTO para requisição de pontos de parada")
@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("idsDevices")
                                  @Schema(description = "Lista de identificadores dos dispositivos",
                                          example = "[1, 2, 3]")
                                  List<Long> devices,

                                  @JsonAlias("startDate")
                                  @Schema(description = "Data de início do filtro",
                                          example = "2024-01-01")
                                  LocalDate startDate,

                                  @JsonAlias("finalDate")
                                  @Schema(description = "Data de término do filtro",
                                          example = "2024-01-31")
                                  LocalDate finalDate
) {
}
