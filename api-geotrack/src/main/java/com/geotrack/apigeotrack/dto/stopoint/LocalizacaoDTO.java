package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocalizacaoDTO(@JsonAlias("latitude") BigDecimal latitude,
                             @JsonAlias("longitude") BigDecimal longitude
                             ){

}
