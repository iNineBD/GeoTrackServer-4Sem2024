package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PropertiesDTO", description = "DTO para Propriedades")
@JsonIgnoreProperties(ignoreUnknown = true)
public record PropertiesDTO() {
}
