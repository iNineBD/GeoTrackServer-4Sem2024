package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.service.StopPointSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Tag(name = "Pontos de parada na Zona Geográfica", description = "Operações para obter pontos de parada dentro de uma zona geográfica")
@RestController
@RequestMapping("/stoppointsession")
@CrossOrigin(origins = "*")
public class StopPointSessionController {
    @Autowired
    StopPointSessionService stopPointSessionService;

    @Operation(summary = "Retorna os pontos de parada de um dispositivo em uma zona geográfica", description = "Retorna os pontos de parada de um dispositivo em uma zona geográfica em um intervalo de tempo")
    @GetMapping("/pointsInSession")
    public StopPointSessionResponseDTO pointsInSession(@Parameter(description = "Identificador do dispositivo", example = "1", required = true)
                                                           @RequestParam Long deviceId,

                                                       @Parameter(description = "Data de início para o filtro", example = "2024-08-01", required = true)
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

                                                       @Parameter(description = "Data de fim para o filtro", example = "2024-08-31", required = true)
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,

                                                       @Parameter(description = "Latitude da localização de referência", example = "-23.550520", required = true)
                                                           @RequestParam BigDecimal latitude,

                                                       @Parameter(description = "Longitude da localização de referência", example = "-46.633308", required = true)
                                                           @RequestParam BigDecimal longitude,

                                                       @Parameter(description = "Raio da área de busca em metros", example = "166.6440826868925", required = true)
                                                           @RequestParam double radius) {

        CenterCoordinatesDTO coordinates = new CenterCoordinatesDTO(longitude,latitude);
        StopPointSessionRequestDTO stopPointSessionRequestDTO = new StopPointSessionRequestDTO(
                deviceId, startDate, endDate, coordinates, radius);

        return stopPointSessionService.stopPointInSession(stopPointSessionRequestDTO);
    }
}
