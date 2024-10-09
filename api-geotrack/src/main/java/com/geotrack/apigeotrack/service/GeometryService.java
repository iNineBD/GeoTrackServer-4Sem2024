package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.geometry.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.entities.GeometrySession;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import com.geotrack.apigeotrack.service.utils.CoordinatesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeometryService {

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    // method to insert geometry zones in database
    public void insertGeometryZones(GeometryZoneRequestDTO geometryZoneRequestDTO) {

        if (geometryZoneRequestDTO.name().isEmpty()) {
            throw new IllegalArgumentException("Nome vazio!");
        }

        if (geometryZoneRequestDTO.type() == null) {
            throw new IllegalArgumentException("Tipo da Zona Geométrica vazio!");
        }

        // validate if long and lat are null and validate if are valid true
        CoordinatesValidator.validatesCoordinator(geometryZoneRequestDTO.center().longitude(),
                geometryZoneRequestDTO.center().latitude());

        // validate radius are null
        CoordinatesValidator.validatesRadius(geometryZoneRequestDTO.radius());

        // create object to send
        GeometrySession geometrySession = new GeometrySession();
        geometrySession.setName(geometryZoneRequestDTO.name());
        geometrySession.setType(geometryZoneRequestDTO.type());
        geometrySession.setLongitude(geometryZoneRequestDTO.center().longitude());
        geometrySession.setLatitude(geometryZoneRequestDTO.center().latitude());
        geometrySession.setRadius(geometryZoneRequestDTO.radius());

        geometryZoneRepository.save(geometrySession);
    }
}
