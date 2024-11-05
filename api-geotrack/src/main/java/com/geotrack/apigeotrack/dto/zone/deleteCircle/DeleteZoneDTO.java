package com.geotrack.apigeotrack.dto.zone.deleteCircle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DeleteZoneDTO", description = "DTO para deletar zona")
@JsonIgnoreProperties(ignoreUnknown = true)
public record DeleteZoneDTO(@JsonAlias("id")
                            @Schema(name = "Identificador da Zona",example = "1") Integer id) {
}
