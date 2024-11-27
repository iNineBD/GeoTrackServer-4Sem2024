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

                             @JsonAlias("latitude")
                             @Schema(description = "Latitude média do dispositivo", example = "-23.550520")
                             BigDecimal latitude,

                             @JsonAlias("longitude")
                             @Schema(description = "Longitude média do dispositivo", example = "-46.633308")
                             BigDecimal longitude,

                             @JsonAlias("media_latitude")
                             @Schema(description = "Contador de eventos ou registros relacionados ao dispositivo", example = "10")
                             BigDecimal avgLatitude,

                             @JsonAlias("media_longitude")
                             @Schema(description = "Grupo de localizações que são potenciais pontos de parada", example = "10")
                             BigDecimal avgLongitude,

                             @JsonAlias("data_hora")
                             @Schema(description = "Longitude no momento de registro", example = "-46.633308")
                             LocalDateTime dataHora,

                             @JsonAlias("data_inicio")
                             @Schema(description = "Longitude no momento de registro", example = "-46.633308")
                             LocalDateTime startTime,

                             @JsonAlias("data_fim")
                             @Schema(description = "Longitude no momento de registro", example = "-46.633308")
                             LocalDateTime endTime,

                             @JsonAlias("grupo_localizacao")
                             @Schema(description = "Latitude no momento de registro", example = "-23.550520")
                             int grupoLocalizacao) {

}
