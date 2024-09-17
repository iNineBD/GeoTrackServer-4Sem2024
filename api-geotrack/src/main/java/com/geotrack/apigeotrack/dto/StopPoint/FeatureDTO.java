package com.geotrack.apigeotrack.dto.StopPoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureDTO(@JsonAlias("type") String type,
                         @JsonAlias("properties")String properties,
                         @JsonAlias("geometry")GeometryDTO geometry) {
    public FeatureDTO {
        properties = "";
        type = "Feature";
    }
}
