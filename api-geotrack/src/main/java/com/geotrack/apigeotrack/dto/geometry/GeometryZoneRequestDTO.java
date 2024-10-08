package com.geotrack.apigeotrack.dto.geometry;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.service.utils.GeometryForms;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryZoneRequestDTO(
        @JsonAlias("name") String name, // zone name
        @JsonAlias("type") GeometryForms type, // enum geometry zones
        @JsonAlias("center") CenterCoordinatesData center,
        @JsonAlias("radius") BigDecimal radius) // coordinates list
{
}
