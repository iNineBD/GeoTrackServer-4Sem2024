package com.geotrack.apigeotrack.dto.zone.insert;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CenterCoordinatesDTO(
        @JsonAlias("longitude") BigDecimal longitude,
        @JsonAlias("latitude") BigDecimal latitude) {
}
