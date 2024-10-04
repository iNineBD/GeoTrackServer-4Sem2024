package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointDBDTO(@JsonAlias("avg_latitude") BigDecimal latitude,
                             @JsonAlias("avg_longitude") BigDecimal longitude,
                             @JsonAlias("contador") int contador,
                             @JsonAlias("lat_long_list") String latLongList,
                             @JsonAlias("start_time") LocalDateTime startDate,
                             @JsonAlias("start_time") LocalDateTime endDate) {

}
