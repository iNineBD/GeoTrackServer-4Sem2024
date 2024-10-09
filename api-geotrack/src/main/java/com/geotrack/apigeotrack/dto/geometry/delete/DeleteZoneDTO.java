package com.geotrack.apigeotrack.dto.geometry.delete;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeleteZoneDTO(@JsonAlias("id") Integer id) {
}
