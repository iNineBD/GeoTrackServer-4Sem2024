package com.geotrack.apigeotrack.dto.zone.deleteCircle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeleteZoneDTO(@JsonAlias("id") Integer id) {
}
