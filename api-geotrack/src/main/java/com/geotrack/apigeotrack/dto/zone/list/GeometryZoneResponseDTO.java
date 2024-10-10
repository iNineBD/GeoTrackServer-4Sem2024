package com.geotrack.apigeotrack.dto.zone.list;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.zone.insert.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.utils.GeometryForms;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryZoneResponseDTO(@JsonAlias("id") Integer id,
                                      @JsonAlias("name") String name,
                                      @JsonAlias("type") GeometryForms type,
                                      @JsonAlias("center") CenterCoordinatesDTO center,
                                      @JsonAlias("radius") BigDecimal radius) {
}
