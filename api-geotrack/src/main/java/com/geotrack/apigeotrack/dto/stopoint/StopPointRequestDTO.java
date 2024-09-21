package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("idUser") int user,
                                  @JsonAlias("userName") String userName,
                                  @JsonAlias("idDevice") Long device,
                                  @JsonAlias("userDevice") String userDevice,
                                  @JsonAlias("startDate") Timestamp startDate,
                                  @JsonAlias("finalDate") Timestamp finalDate
) {
}
