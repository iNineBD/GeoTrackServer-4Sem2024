package com.geotrack.apigeotrack.dto.StopPoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PropertiesDTO(@JsonAlias("properties") String properties) {
    public PropertiesDTO {
        properties = "properties";
    }
}
