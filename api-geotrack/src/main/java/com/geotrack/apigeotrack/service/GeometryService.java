package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.geometry.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.entities.GeometryCoordinates;
import com.geotrack.apigeotrack.entities.GeometryZone;
import com.geotrack.apigeotrack.repositories.GeometryCoordinatesRepository;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import com.geotrack.apigeotrack.service.utils.CoordinatesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeometryService {

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    @Autowired
    GeometryCoordinatesRepository geometryCoordinatesRepository;

    // method to insert geometric zones coordinates
    public void insertGeometryZone(GeometryZoneRequestDTO geometryZoneRequestDTO) throws IllegalArgumentException {

        // verify if name is not empty
        if (geometryZoneRequestDTO.name().isEmpty()) {
            throw new IllegalArgumentException("Defina um nome para sua Zona Geométrica!");
        }
        // verify if coordinates list is not empty
        if(geometryZoneRequestDTO.coordinates().isEmpty()){
            throw new IllegalArgumentException("Lista de coordenadas vazia, revise!");
        }

        // create object to send
        GeometryZone geometryZone = new GeometryZone();
        geometryZone.setNome(geometryZoneRequestDTO.name());
        geometryZone.setType(geometryZoneRequestDTO.type());

        // loop to create coordinates object
        List<GeometryCoordinates> coordinatesEntities = geometryZoneRequestDTO.coordinates().stream()
                .map(coordDTO -> {

                    // verify long and lag is not null or valid,
                    CoordinatesValidator.validatesCoordinator(coordDTO.longitude(), coordDTO.latitude());

                    GeometryCoordinates geometryCoordinates = new GeometryCoordinates();
                    geometryCoordinates.setLongitude(coordDTO.longitude());
                    geometryCoordinates.setLatitude(coordDTO.latitude());
                    return geometryCoordinates;
                }).collect(Collectors.toList());

        geometryZone.setCoordinates(coordinatesEntities);
        geometryZone.setStatus(1);

        // save the object and send to database
        geometryZoneRepository.save(geometryZone);
    }

}
