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

    // metodo para inserir as coordenadas da zona geometrica
    public void insertGeometryZone(GeometryZoneRequestDTO geometryZoneRequestDTO) throws IllegalArgumentException {

        // verifica se o nome não esta vazio
        if (geometryZoneRequestDTO.name().isEmpty()) {
            throw new IllegalArgumentException("Defina um nome para sua Zona Geometrica!");
        }

        // cria objeto para envio
        GeometryZone geometryZone = new GeometryZone();
        geometryZone.setNome(geometryZoneRequestDTO.name());
        geometryZone.setType(geometryZoneRequestDTO.type());

        // Agora, cria as entidades GeometryCoordinates com base nos dados do requestDTO
        List<GeometryCoordinates> coordinatesEntities = geometryZoneRequestDTO.coordinates().stream()
                .map(coordDTO -> {

                    CoordinatesValidator.validatesCoordinator(coordDTO.longitude(), coordDTO.latitude());

                    GeometryCoordinates geometryCoordinates = new GeometryCoordinates();
                    geometryCoordinates.setLongitude(coordDTO.longitude());
                    geometryCoordinates.setLatitude(coordDTO.latitude());
                    return geometryCoordinates; // Retorna cada entidade GeometryCoordinates
                }).collect(Collectors.toList());

        // relaciona com a chave estrangeira em geometryZone
        geometryZone.setCoordinates(coordinatesEntities);

        // salva a GeometryZone com suas coordenadas
        geometryZoneRepository.save(geometryZone);
    }

}
