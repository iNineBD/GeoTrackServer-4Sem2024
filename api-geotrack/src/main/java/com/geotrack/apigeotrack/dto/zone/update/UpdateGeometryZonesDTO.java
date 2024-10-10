package com.geotrack.apigeotrack.dto.zone.update;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insert.CenterCoordinatesDTO;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateGeometryZonesDTO(
        @JsonAlias("id") Integer id,
        @JsonAlias("name") String name,
        @JsonAlias("type") String type,
        @JsonAlias("center") CenterCoordinatesDTO center,
        @JsonAlias("radius") BigDecimal radius) {
}
