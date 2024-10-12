package com.geotrack.apigeotrack.dto.stopointsessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointSessionRequestDTO(@JsonAlias("idDevice") Long deviceId,
                                         @JsonAlias("startDate") LocalDate startDate,
                                         @JsonAlias("endDate") LocalDate endDate,
                                         @JsonAlias("centerCoordinates") CenterCoordinatesDTO coordinates,
                                         @JsonAlias("radius") double radius) {
}
