package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored.MetricsResponse;
import com.geotrack.apigeotrack.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    MetricsService metricsService;

    @GetMapping
    public ResponseEntity<MetricsResponse> fullSessionsAndMOnitored (){

        MetricsResponse response = metricsService.getQtdSessionsAndMonitored();

        return ResponseEntity.ok().body(response);
    }
}
