package com.geotrack.apigeotrack.controllers;


import com.geotrack.apigeotrack.dto.routes.find.ResponseFindRoutesDTO;
import com.geotrack.apigeotrack.dto.zone.getAllCircle.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.service.RouteService;
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

@Tag(name = "Routes", description = "Operations to find routes")
@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;


    @GetMapping
    public ResponseEntity<List<ResponseFindRoutesDTO>> findRoutes(@RequestParam Long deviceId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime start = LocalDateTime.now();
        List<ResponseFindRoutesDTO> response = routeService.findRoutes(deviceId, date);
        System.out.println("Time to find routes: " + java.time.Duration.between(start, LocalDateTime.now()).toString());
        return ResponseEntity.ok(response);

    }

}
