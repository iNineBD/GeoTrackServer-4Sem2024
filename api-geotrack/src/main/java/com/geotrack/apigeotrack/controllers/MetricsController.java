package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored.MetricsResponse;
import com.geotrack.apigeotrack.service.MetricsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Metrics",description = "Return of total monitored and active sessions metrics")
@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    MetricsService metricsService;

    @Operation(summary = "Total return of monitored and active sessions", description = "Returns data on the number of monitored people saved in the system and also the number of active sessions")
    @GetMapping
    public ResponseEntity<MetricsResponse> fullSessionsAndMOnitored (){

        MetricsResponse response = metricsService.getQtdSessionsAndMonitored();

        return ResponseEntity.ok().body(response);
    }
}
