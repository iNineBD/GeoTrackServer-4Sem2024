package com.geotrack.apigeotrack.dto.routes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class RouteSQLDTO {

    private int idDev;
    private String coordinates;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private int cordinatesCount;

    public RouteSQLDTO(int idDev, String coordinates, LocalDateTime startDate, LocalDateTime stopDate, int cordinatesCount) {
        this.idDev = idDev;
        this.coordinates = coordinates;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.cordinatesCount = cordinatesCount;
    }
}
