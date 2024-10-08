package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointResponseDTO(@JsonAlias("nameUser") String user,
                                   @JsonAlias("nameDevice") String device,
                                   @JsonAlias("geojson") GeoJsonDTO geoJsonDTO) {
}
