package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocalizacaoDTO(@JsonAlias("latitude") BigDecimal latitude,
                             @JsonAlias("longitude") BigDecimal longitude,
                             @JsonAlias("dataHora") Timestamp dataHora) {

}
