package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "GeoJsonDTO", description = "DTO para GeoJson")
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeoJsonDTO(@JsonAlias("type")
                         @Schema(description = "Tipo de coleção de recursos", example = "FeatureCollection")
                         String type,

                         @JsonAlias("features")
                         @Schema(description = "Lista de recursos presentes na coleção")
                         List<FeatureDTO> features) {
}
