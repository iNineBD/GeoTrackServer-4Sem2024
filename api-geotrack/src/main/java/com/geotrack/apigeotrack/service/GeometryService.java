package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.geometry.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.geometry.ZoneCoordenatesDTO;
import com.geotrack.apigeotrack.entities.GeometryCoordinates;
import com.geotrack.apigeotrack.entities.GeometryZone;
import com.geotrack.apigeotrack.repositories.GeometryCoordinatesRepository;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        // verificação das coordenadas
        for (ZoneCoordenatesDTO coordinates : geometryZoneRequestDTO.coordinates()) {

            // verifica se long e lati não estão vazios
            if (coordinates.longitude() == null) {
                throw new IllegalArgumentException("Longitude não pode ser nula.");
            }
            if (coordinates.latitude() == null) {
                throw new IllegalArgumentException("Latitude não pode ser nula.");
            }
            // verifica as coordenadas (longitude e latitude)
            if (coordinates.longitude().compareTo(BigDecimal.valueOf(-180)) < 0 ||
                    coordinates.longitude().compareTo(BigDecimal.valueOf(180)) > 0) {
                throw new IllegalArgumentException("Longitude inválida: " + coordinates.longitude() + ". Deve estar " +
                        "entre -180 e 180 graus.");
            }
            if (coordinates.latitude().compareTo(BigDecimal.valueOf(-90)) < 0 ||
                    coordinates.latitude().compareTo(BigDecimal.valueOf(90)) > 0) {
                throw new IllegalArgumentException("Latitude inválida: " + coordinates.latitude() + ". Deve estar " +
                        "entre -90 e 90 graus.");
            }
        }

        // cria objeto para envio
        GeometryZone geometryZone = new GeometryZone();
        geometryZone.setNome(geometryZoneRequestDTO.name());
        geometryZone.setType(geometryZoneRequestDTO.type());

        // salva GeometryZone primeiro para gerar o id
        geometryZoneRepository.save(geometryZone);

        // Agora, cria as entidades GeometryCoordinates com base nos dados do requestDTO
        List<GeometryCoordinates> coordinatesEntities = geometryZoneRequestDTO.coordinates().stream()
                .map(coordDTO -> {
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
