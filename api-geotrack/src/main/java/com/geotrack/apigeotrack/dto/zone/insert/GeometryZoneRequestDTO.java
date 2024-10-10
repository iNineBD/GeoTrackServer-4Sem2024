package com.geotrack.apigeotrack.dto.zone.insert;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.service.utils.GeometryForms;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryZoneRequestDTO(
        @JsonAlias("name") String name, // zone name
        @JsonAlias("type") GeometryForms type, // enum zone zones
        @JsonAlias("center") CenterCoordinatesDTO center,
        @JsonAlias("radius") BigDecimal radius) // coordinates list
{
}
