package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.geometry.delete.DeleteZoneDTO;
import com.geotrack.apigeotrack.dto.geometry.update.UpdateGeometryZonesDTO;
import com.geotrack.apigeotrack.dto.geometry.insert.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.geometry.listAll.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.service.GeometryService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Adiciona uma Zona Geométrica ao Banco de dados")
    @PostMapping("/insert")
    public ResponseEntity<String> insertCircle(@RequestBody GeometryZoneRequestDTO geometryZoneRequestDTO) {
        geometryService.insertGeometryZones(geometryZoneRequestDTO);
        return new ResponseEntity<>("Zona inserida com Sucesso", HttpStatus.CREATED);
    }

    @Operation(summary = "Listagem de Zonas Geométricas", description = "Retorna uma lista de Zonas Geométricas ativas no banco de dados")
    @GetMapping("/list")
    public ResponseEntity<List<GeometryZoneResponseDTO>> getAllZones() {
        geometryService.listAllGeometryZones();
        return ResponseEntity.ok().body(geometryService.listAllGeometryZones());
    }

    @Operation(summary = "Deleta uma Zona Geométrica", description = "Deleta uma Zona Geométrica do banco de dados, alterando o status para 0")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteGeometryZones(@RequestBody DeleteZoneDTO deleteZoneDTO) {
        geometryService.deleteZones(deleteZoneDTO);
        return new ResponseEntity<>("Zona deletada com Sucesso", HttpStatus.OK);
    }

    @Operation(summary = "Edita uma Zona Geométrica", description = "Recebe os dados alterados de uma zona geométrica, e salva-os no banco de dados")
    @PutMapping("/update")
    public ResponseEntity<String> updateGeometryZones(@RequestBody UpdateGeometryZonesDTO updateGeometryZonesDTO){
        geometryService.editZones(updateGeometryZonesDTO);
        return new ResponseEntity<>("Zona Geométrica editada com Sucesso",HttpStatus.OK);
    }

}