package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Localizacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryDTO(@JsonAlias("type") String type,
                          @JsonAlias("coordinates") Set<BigDecimal> coordinates ) {
}
