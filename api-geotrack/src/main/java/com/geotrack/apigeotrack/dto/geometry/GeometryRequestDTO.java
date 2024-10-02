package com.geotrack.apigeotrack.dto.geometry;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.locationtech.jts.geom.Coordinate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryRequestDTO(
        @JsonAlias("name") String name,             // Nome associado à geometria
        @JsonAlias("type") String geometryType,      // Tipo de geometria: "Point", "LineString", "Polygon", etc.
        @JsonAlias("coordinates") Coordinate[] coordinates  // Array de coordenadas para representar a geometria
) {
}
