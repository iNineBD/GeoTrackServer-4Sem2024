package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.geometry.insert.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.geometry.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.service.GeometryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Geometry Zones", description = "Operations to geometry zones to insert, listing and exclusion")
@RestController
@RequestMapping("/zone")
public class GeometryController {

    @Autowired
    GeometryService geometryService;

    // method to insert geometry zones
    @PostMapping("/add")
    public ResponseEntity<String> insertGeometryZones(@RequestBody GeometryZoneRequestDTO geometryZoneRequestDTO) {
        geometryService.insertGeometryZones(geometryZoneRequestDTO);
        return new ResponseEntity<>("Zone inserted successfully", HttpStatus.CREATED);
    }

    @GetMapping("list")
    public ResponseEntity<List<GeometryZoneResponseDTO>> getAllZones(){
        geometryService.getAll();
        return ResponseEntity.ok().body(geometryService.getAll());
    }





}