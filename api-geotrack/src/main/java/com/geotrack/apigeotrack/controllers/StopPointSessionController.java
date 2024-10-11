package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.service.StopPointSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Stop Points in Session", description = "Operations to return stopping points of devices in Geographics Sessions")
@RestController
@RequestMapping("/stoppointsession")
public class StopPointSessionController {
    @Autowired
    StopPointSessionService stopPointSessionService;

    @GetMapping("/search")
    public StopPointSessionResponseDTO pointsInSession (@RequestBody StopPointSessionRequestDTO stopPointSessionRequestDTO){
       return stopPointSessionService.stopPointInSession(stopPointSessionRequestDTO);
    }

}
