package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.fullSessionsAndMOnitored.MetricsResponse;
import com.geotrack.apigeotrack.service.MetricsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Métricas",description = "Operações para obter métricas do sistema")
@RestController
@RequestMapping("/metrics")
@CrossOrigin(origins = "*")
public class MetricsController {

    @Autowired
    MetricsService metricsService;

    @Operation(summary = "Retorna a quantidade de zonas geográficas, usuários administradores e dispositivos monitorados", description = "Retorna a quantidade de zonas geográficas, usuários administradores e dispositivos monitorados")
    @GetMapping
    public ResponseEntity<MetricsResponse> fullSessionsAndMOnitored (){

        MetricsResponse response = metricsService.getQtdSessionsAndMonitored();

        return ResponseEntity.ok().body(response);
    }
}
