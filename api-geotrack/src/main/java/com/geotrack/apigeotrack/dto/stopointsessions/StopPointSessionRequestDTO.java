package com.geotrack.apigeotrack.dto.stopointsessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.geometry.ZoneCoordenatesDTO;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointSessionRequestDTO(@JsonAlias("idUser") int userId,
                                         @JsonAlias ("userName") String userName,
                                         @JsonAlias("idDevice") int deviceId,
                                         @JsonAlias("device") String deviceName,
                                         @JsonAlias("startDate")LocalDate startDate,
                                         @JsonAlias("endDate") LocalDate endDate,
                                         @JsonAlias("cordinates")List<ZoneCoordenatesDTO> cordinates) {
}
