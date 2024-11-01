package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeometryDTO(@JsonAlias("type") String type,
                          @JsonAlias("coordinates") BigDecimal[] coordinates,
                          @JsonAlias("start_time") String startDate,
                          @JsonAlias("end_time") String endDate) {
}
