package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryDTO(@JsonAlias("type") String type,
                          @JsonAlias("coordinates") List<BigDecimal> coordinates ) {
    public GeometryDTO {
        type = "Point";
    }
}
