package com.geotrack.apigeotrack.controllers;

import com.geotrack.apigeotrack.dto.stopoint.*;
import com.geotrack.apigeotrack.entities.Dispositivo;
import com.geotrack.apigeotrack.repositories.LocalizacaoRepository;
import com.geotrack.apigeotrack.service.StopPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Optional<Dispositivo> device =  stopPointService.userDeviceNames(requestDTO);
        String userName = device.get().getUsuario().getNome();
        String deviceName = device.get().getNome();
        Set<LocalizacaoDTO> pontosParada = stopPointService.latLongCal(requestDTO);
        List<FeatureDTO> feature = stopPointService.resquestGeoJson(pontosParada);


        GeoJsonDTO geoJson = new GeoJsonDTO("FeatureCollection",feature);

        return new StopPointResponseDTO(userName,deviceName,geoJson);
    }

}
