package com.geotrack.apigeotrack.dto.stopointsessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.geometry.CenterCoordinatesData;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointSessionRequestDTO(@JsonAlias("idDevice") Long deviceId,
                                         @JsonAlias("device") String deviceName,
                                         @JsonAlias("startDate")LocalDate startDate,
                                         @JsonAlias("endDate") LocalDate endDate,
                                         @JsonAlias("centerCoordinates") CenterCoordinatesData coordinates,
                                         @JsonAlias("radius")double radius) {
}
