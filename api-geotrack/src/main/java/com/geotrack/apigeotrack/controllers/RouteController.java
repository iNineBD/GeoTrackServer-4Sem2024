package com.geotrack.apigeotrack.controllers;


import com.geotrack.apigeotrack.dto.routes.find.ResponseFindRoutesDTO;
import com.geotrack.apigeotrack.dto.zone.getAllCircle.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Tag(name = "Routes", description = "Operations to find routes")
@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;


    @Operation(summary = "Retorna uma lista das rotas identificadas para o dispositivo buscado", description = "Consulta as rotas identificadas para o dispositivo buscado entre as datas informadas")
    @GetMapping
    public ResponseEntity<Map<String,List<ResponseFindRoutesDTO>>> findRoutes(@RequestParam Long deviceId,
                                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd) {
        LocalDateTime start = LocalDateTime.now();
        List<ResponseFindRoutesDTO> response = routeService.findRoutes(deviceId, dateStart, dateEnd);
        System.out.println("Time to find routes: " + java.time.Duration.between(start, LocalDateTime.now()).toString());
        return ResponseEntity.ok(Map.of("routes", response));

    }

}
