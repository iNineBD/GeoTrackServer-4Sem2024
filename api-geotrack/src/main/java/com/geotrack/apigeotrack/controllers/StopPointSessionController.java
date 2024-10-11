package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionRequestDTO;
import com.geotrack.apigeotrack.dto.stopointsessions.StopPointSessionResponseDTO;
import com.geotrack.apigeotrack.service.StopPointSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
