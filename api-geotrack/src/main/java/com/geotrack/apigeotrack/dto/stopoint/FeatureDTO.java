package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureDTO(@JsonAlias("type") String type,
                         @JsonAlias("properties") PropertiesDTO properties,
                         @JsonAlias("geometry") GeometryDTO geometry) {
}
