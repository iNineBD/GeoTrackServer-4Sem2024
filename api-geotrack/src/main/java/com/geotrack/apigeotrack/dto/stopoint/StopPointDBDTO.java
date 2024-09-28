package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointDBDTO(@JsonAlias("time_group") int timeGroup,
                             @JsonAlias("avg_latitude") BigDecimal latitude,
                             @JsonAlias("avg_longitude") BigDecimal longitude,
                             @JsonAlias("count") int count,
                             @JsonAlias("lat_long_list") String latLongList) {

}
