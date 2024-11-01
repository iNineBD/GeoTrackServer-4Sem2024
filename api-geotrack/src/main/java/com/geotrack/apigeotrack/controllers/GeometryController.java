package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.zone.deleteCircle.DeleteZoneDTO;
import com.geotrack.apigeotrack.dto.zone.updateCircle.UpdateGeometryZonesDTO;
import com.geotrack.apigeotrack.dto.zone.insertCircle.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.zone.getAllCircle.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.service.GeometryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Zonas Geográficas", description = "Operações com Zonas Geométricas")
@RestController
@RequestMapping("/zone")
public class GeometryController {

    @Autowired
    GeometryService geometryService;

    // method to insertCircle zone zones
    @Operation(summary = "Adiciona uma Zona Geométrica ao Banco de dados", description = "Recebe os dados de uma Zona Geométrica e salva no banco de dados")
    @PostMapping
    public ResponseEntity<String> insertCircle(@RequestBody GeometryZoneRequestDTO geometryZoneRequestDTO) {
        geometryService.insertGeometryZones(geometryZoneRequestDTO);
        return new ResponseEntity<>("Zona inserida com Sucesso", HttpStatus.CREATED);
    }

    @Operation(summary = "Listagem de Zonas Geográficas", description = "Retorna uma lista de Zonas Geográficas ativas no banco de dados")
    @GetMapping
    public ResponseEntity<List<GeometryZoneResponseDTO>> getAllCircle() {
        geometryService.listAllGeometryZones();
        return ResponseEntity.ok().body(geometryService.listAllGeometryZones());
    }

    @Operation(summary = "Deleta uma Zona Geográfica", description = "Deleta uma Zona Geográfica do banco de dados, alterando o status para 0")
    @DeleteMapping
    public ResponseEntity<String> deleteCircle(@RequestBody DeleteZoneDTO deleteZoneDTO) {
        geometryService.deleteZones(deleteZoneDTO);
        return new ResponseEntity<>("Zona deletada com Sucesso", HttpStatus.OK);
    }

    @Operation(summary = "Edita uma Zona Geográfica", description = "Recebe os dados alterados de uma zona geográfica, e salva-os no banco de dados")
    @PutMapping
    public ResponseEntity<String> updateCircle(@RequestBody UpdateGeometryZonesDTO updateGeometryZonesDTO){
        geometryService.editZones(updateGeometryZonesDTO);
        return new ResponseEntity<>("Zona Geométrica editada com Sucesso",HttpStatus.OK);
    }

}