package com.geotrack.apigeotrack.dto.geometry;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterCoordinatesData(
        @JsonAlias("longitude") BigDecimal longitude,
        @JsonAlias("latitude") BigDecimal latitude) {
}
