package com.geotrack.apigeotrack.dto.geometry;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.locationtech.jts.geom.Coordinate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryRequestDTO(
        @JsonAlias("name") String name,
        @JsonAlias("coordinates") Coordinate[] coordinates) {
}
