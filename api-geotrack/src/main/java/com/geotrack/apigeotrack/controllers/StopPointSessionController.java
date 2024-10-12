package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.StopPointSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Tag(name = "Stop Points in Session", description = "Operations to return stopping points of devices in Geographics Sessions")
@RestController
@RequestMapping("/stoppointsession")
public class StopPointSessionController {
    @Autowired
    StopPointSessionService stopPointSessionService;

    @GetMapping("/pointsInSession/{deviceId}/{startDate}/{endDate}/{latitude}/{longitude}/{radius}")
    public StopPointSessionResponseDTO pointsInSession(@PathVariable Long deviceId,
                                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                       @PathVariable BigDecimal latitude,
                                                       @PathVariable BigDecimal longitude,
                                                       @PathVariable double radius) {

        CenterCoordinatesDTO coordinates = new CenterCoordinatesDTO(latitude, longitude);
        StopPointSessionRequestDTO stopPointSessionRequestDTO = new StopPointSessionRequestDTO(
                deviceId, startDate, endDate, coordinates, radius);

        return stopPointSessionService.stopPointInSession(stopPointSessionRequestDTO);
    }
}
