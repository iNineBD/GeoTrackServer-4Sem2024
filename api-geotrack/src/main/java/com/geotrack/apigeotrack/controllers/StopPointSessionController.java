package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.StopPointSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Tag(name = "Pontos de parada na Zona Geográfica", description = "Operações para obter pontos de parada dentro de uma zona geográfica")
@RestController
@RequestMapping("/stoppointsession")
public class StopPointSessionController {
    @Autowired
    StopPointSessionService stopPointSessionService;

    @Operation(summary = "Retorna os pontos de parada de um dispositivo em uma zona geográfica", description = "Retorna os pontos de parada de um dispositivo em uma zona geográfica em um intervalo de tempo")
    @GetMapping("/pointsInSession")
    public StopPointSessionResponseDTO pointsInSession(@RequestParam Long deviceId,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                       @RequestParam BigDecimal latitude,
                                                       @RequestParam BigDecimal longitude,
                                                       @RequestParam double radius) {

        CenterCoordinatesDTO coordinates = new CenterCoordinatesDTO(longitude,latitude);
        StopPointSessionRequestDTO stopPointSessionRequestDTO = new StopPointSessionRequestDTO(
                deviceId, startDate, endDate, coordinates, radius);

        return stopPointSessionService.stopPointInSession(stopPointSessionRequestDTO);
    }
}
