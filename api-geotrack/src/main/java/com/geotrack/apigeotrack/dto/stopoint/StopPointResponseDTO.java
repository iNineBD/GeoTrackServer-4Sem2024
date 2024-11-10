package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "StopPointResponseDTO", description = "DTO para resposta de ponto de parada")
@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointResponseDTO(@JsonAlias("nameUser")
                                   @Schema(description = "Nome do usuário associado ao dispositivo",
                                           example = "João Silva")
                                   String user,

                                   @JsonAlias("nameDevice")
                                   @Schema(description = "Nome do dispositivo",
                                           example = "Dispositivo XYZ")
                                   String device,

                                   @JsonAlias("geojson")
                                   @Schema(description = "Representação geográfica em formato GeoJSON")
                                   GeoJsonDTO geoJsonDTO) {
}
