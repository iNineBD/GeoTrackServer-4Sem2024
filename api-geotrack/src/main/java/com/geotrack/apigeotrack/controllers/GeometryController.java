package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.geometry.GeometryRequestDTO;
import com.geotrack.apigeotrack.service.GeometryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geometry")
public class GeometryController {

    private final GeometryService geometryService;

    @Autowired
    public GeometryController(GeometryService geometryService) {
        this.geometryService = geometryService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addGeometry(@RequestBody GeometryRequestDTO geometryRequestDTO){

        geometryService.insertGeometry(geometryRequestDTO);

        return ResponseEntity.ok().body("Sessão Geografica cadastrada");

    }

}