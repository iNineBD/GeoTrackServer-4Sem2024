package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.service.StopPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "stopoint", description = "Operations to return stopping points of the users and devices")
@RestController
@RequestMapping("/stop-point")
public class StopPointController {

    @Autowired
    StopPointService stopPointService;
    @Autowired
    LocalizacaoRepository localizacaoRepository;

    @PostMapping
    public StopPointResponseDTO stopPointResponseDTO(@RequestBody StopPointRequestDTO requestDTO) {
        String user = stopPointService.userDeviceNames(requestDTO).get().getUsuario().getNome();
        String device = stopPointService.userDeviceNames(requestDTO).get().getNome();

        List<FeatureDTO> feature = stopPointService.latLongCal(requestDTO);
        GeoJsonDTO geoJson = new GeoJsonDTO("FeatureCollection",feature);

        return new StopPointResponseDTO(user,device,geoJson);
    }

}
