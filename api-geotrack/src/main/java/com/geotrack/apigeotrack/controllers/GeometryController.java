package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.geometry.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.service.GeometryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zone")
public class GeometryController {

    @Autowired
    GeometryService geometryService;

    @PostMapping("/add")
    public ResponseEntity<String> insertPolygon(@RequestBody GeometryZoneRequestDTO geometryZoneRequestDTO) {
        geometryService.insertGeometryZone(geometryZoneRequestDTO);  // Chama o metodo de inserir a zona
        return new ResponseEntity<>("Zone inserted successfully", HttpStatus.CREATED);
    }

}