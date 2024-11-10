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
import java.util.Map;

@Tag(name = "Áreas Geográficas", description = "Operações com Áreas Geográficas")
@RestController
@RequestMapping("/zone")
@CrossOrigin(origins = "*")
public class GeometryController {

    @Autowired
    GeometryService geometryService;

    // method to insertCircle zone zones
    @Operation(summary = "Adiciona uma Área Geográfica ao Banco de dados", description = "Recebe os dados de uma Área Geográfica e salva no banco de dados")
    @PostMapping
    public ResponseEntity<Map<String,String>> insertCircle(@RequestBody GeometryZoneRequestDTO geometryZoneRequestDTO) {
        geometryService.insertGeometryZones(geometryZoneRequestDTO);
        return new ResponseEntity<>(Map.of("message","Área inserida com Sucesso"),HttpStatus.CREATED);
    }

    @Operation(summary = "Listagem de Áreas Geográficas", description = "Retorna uma lista de Áreas Geográficas ativas no banco de dados")
    @GetMapping
    public ResponseEntity<List<GeometryZoneResponseDTO>> getAllCircle() {
        geometryService.listAllGeometryZones();
        return ResponseEntity.ok().body(geometryService.listAllGeometryZones());
    }

    @Operation(summary = "Deleta uma Área Geográfica", description = "Deleta uma Área Geográfica do banco de dados, alterando o status para 0")
    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteCircle(@RequestBody DeleteZoneDTO deleteZoneDTO) {
        geometryService.deleteZones(deleteZoneDTO);
        return new ResponseEntity<>(Map.of("message","Área deletada com Sucesso"), HttpStatus.OK);
    }

    @Operation(summary = "Edita uma Área Geográfica", description = "Recebe os dados alterados de uma Área geográfica, e salva-os no banco de dados")
    @PutMapping
    public ResponseEntity<Map<String,String>> updateCircle(@RequestBody UpdateGeometryZonesDTO updateGeometryZonesDTO){
        geometryService.editZones(updateGeometryZonesDTO);
        return new ResponseEntity<>(Map.of("message","Área Geográfica editada com Sucesso"),HttpStatus.OK);
    }

}