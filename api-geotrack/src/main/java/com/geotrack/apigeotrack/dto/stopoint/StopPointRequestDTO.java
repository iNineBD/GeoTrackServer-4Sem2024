package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("idUser") int user,
                                  @JsonAlias("userName") String userName,
                                  @JsonAlias("idDevice") List<Long> device,
                                  @JsonAlias("userDevice") String userDevice,
                                  @JsonAlias("startDate") LocalDate startDate,
                                  @JsonAlias("finalDate") LocalDate finalDate
) {
}
