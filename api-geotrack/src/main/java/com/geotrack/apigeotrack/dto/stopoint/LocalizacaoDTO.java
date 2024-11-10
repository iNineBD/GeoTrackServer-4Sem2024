package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Schema(name = "LocalizacaoDTO", description = "DTO para Localização")
@JsonIgnoreProperties(ignoreUnknown = true)
public record LocalizacaoDTO(@JsonAlias("latitude")
                             @Schema(description = "Latitude da localização",
                                     example = "-23.550520")
                             BigDecimal latitude,

                             @JsonAlias("longitude")
                             @Schema(description = "Longitude da localização",
                                     example = "-46.633308")
                             BigDecimal longitude,

                             @JsonAlias("start_time")
                             @Schema(description = "Data e hora inicial dos Pontos de parada",
                                     example = "2024-01-01T00:00:00")
                             String startDate,

                             @JsonAlias("end_time")
                             @Schema(description = "Data e hora final dos Pontos de parada",
                                     example = "2024-01-31T23:59:59")
                             String endDate) {

}
