package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Schema(name = "GeometryDTO", description = "DTO para Geometria")
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryDTO(@JsonAlias("type")
                          @Schema(description = "Tipo da geometria", example = "CIRCLE")
                          String type,

                          @JsonAlias("coordinates")
                          @Schema(description = "Coordenadas da geometria, representadas por um array de valores",
                                  example = "[100.0, 0.0]")
                          BigDecimal[] coordinates,

                          @JsonAlias("start_time")
                          @Schema(description = "Data e hora inicial dos Pontos de parada",
                                  example = "2024-01-01T00:00:00")
                          String startDate,

                          @JsonAlias("end_time")
                          @Schema(description = "Data e hora final dos Pontos de parada",
                                  example = "2024-01-31T23:59:59")
                          String endDate) {
}
