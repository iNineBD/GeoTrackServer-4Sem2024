package com.geotrack.apigeotrack.dto.utils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.utils.GeometryForms;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ValidateZonesDTO(
        @JsonAlias("name") String name,
        @JsonAlias("type") GeometryForms type,
        @JsonAlias("center") CenterCoordinatesDTO center,
        @JsonAlias("radius") BigDecimal radius) {
}
