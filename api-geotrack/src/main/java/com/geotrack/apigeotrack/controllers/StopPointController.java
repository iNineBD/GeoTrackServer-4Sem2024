package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.service.StopPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Stop Points", description = "Operations to return stopping points of the users and devices")
@RestController
@RequestMapping("/stoppoint")
public class StopPointController {

    @Autowired
    StopPointService stopPointService;

    @PostMapping("/find")
    public List<StopPointResponseDTO> stopPointResponseDTO(@RequestBody StopPointRequestDTO requestDTO) {
        List<StopPointResponseDTO> pontosParada = stopPointService.findStopPointByDeviceAndData(requestDTO);

//        List<FeatureDTO> feature = stopPointService.resquestGeoJson(pontosParada);
//
//        GeoJsonDTO geoJson = new GeoJsonDTO("FeatureCollection",feature);

        return pontosParada;
    }

}
