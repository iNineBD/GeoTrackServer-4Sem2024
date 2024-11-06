package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "StopPointDBDTO", description = "DTO para Pontos de parada")
@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointDBDTO(@JsonAlias("id_device")
                             @Schema(description = "Identificador do dispositivo", example = "1")
                             int idDev,

                             @JsonAlias("avg_latitude")
                             @Schema(description = "Latitude média do dispositivo", example = "-23.550520")
                             BigDecimal latitude,

                             @JsonAlias("avg_longitude")
                             @Schema(description = "Longitude média do dispositivo", example = "-46.633308")
                             BigDecimal longitude,

                             @JsonAlias("contador")
                             @Schema(description = "Contador de eventos ou registros relacionados ao dispositivo", example = "10")
                             int contador,

                             @JsonAlias("lat_long_list")
                             @Schema(description = "Lista de coordenadas (latitude e longitude) em formato de string",
                                     example = "[[-23.550520, -46.633308], [-23.5564, -46.6422]]")
                             String latLongList,

                             @JsonAlias("start_time")
                             @Schema(description = "Data e hora de início do período de análise", example = "2024-01-01T00:00:00")
                             LocalDateTime startDate,

                             @JsonAlias("end_time")
                             @Schema(description = "Data e hora de término do período de análise", example = "2024-01-31T23:59:59")
                             LocalDateTime endDate) {

}
