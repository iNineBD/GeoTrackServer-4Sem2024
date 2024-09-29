package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeoJsonDTO(@JsonAlias("type") String type,
                         @JsonAlias("features") List<FeatureDTO> features) {
}
