package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.service.StopPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pontos de Parada", description = "Operações para obter pontos de parada")
@RestController
@RequestMapping("/stoppoint")
@CrossOrigin(origins = "*")
public class StopPointController {

    @Autowired
    StopPointService stopPointService;

    @Operation(summary = "Retorna os pontos de parada de um dispositivo", description = "Retorna os pontos de parada de um dispositivo em um intervalo de tempo")
    @PostMapping("/find")
    public List<StopPointResponseDTO> stopPointResponseDTO(@RequestBody StopPointRequestDTO requestDTO) {
        List<StopPointResponseDTO> pontosParada = stopPointService.findStopPointByDeviceAndData(requestDTO);

        return pontosParada;
    }

}
