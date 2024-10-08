package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.geometry.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.service.GeometryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Geometry Zones", description = "Operations to geometry zones to insert, listing and exclusion")
@RestController
@RequestMapping("/zone")
public class GeometryController {

    @Autowired
    GeometryService geometryService;

    // method to insert geometry zones
    @PostMapping("/add")
    public ResponseEntity<String> insertPolygon(@RequestBody GeometryZoneRequestDTO geometryZoneRequestDTO) {
        geometryService.insertGeometryZone(geometryZoneRequestDTO);
        return new ResponseEntity<>("Zone inserted successfully", HttpStatus.CREATED);
    }

}