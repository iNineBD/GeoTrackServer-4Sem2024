package com.geotrack.apigeotrack.dto.stopoint;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointRequestDTO(@JsonAlias("idsUsers") List<Integer> users,
                                  @JsonAlias("usersNames") List<String> usersName,
                                  @JsonAlias("idsDevices") List<Long> device,
                                  @JsonAlias("usersDevices") List<String> userDevice,
                                  @JsonAlias("startDate") LocalDate startDate,
                                  @JsonAlias("finalDate") LocalDate finalDate
) {
}
