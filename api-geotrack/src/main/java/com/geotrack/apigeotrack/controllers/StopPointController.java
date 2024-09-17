package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.StopPoint.StopPointResponseDTO;
import com.geotrack.apigeotrack.dto.StopPoint.StopPointRequestDTO;
import com.geotrack.apigeotrack.service.StopPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "StopPoint", description = "Operations to return stopping points of the users and devices")
@RestController
@RequestMapping("/stop-point")
public class StopPointController {

    @Autowired
    StopPointService stopPointService;

    @PostMapping
    public StopPointResponseDTO stopPointResponseDTO(@RequestBody StopPointRequestDTO requestDTO) {
        StopPointResponseDTO userDevice = stopPointService.stopPointTeste(requestDTO);
        return userDevice;
    }

}
