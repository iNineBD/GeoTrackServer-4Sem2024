package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.zone.delete.DeleteZoneDTO;
import com.geotrack.apigeotrack.dto.zone.insert.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.dto.zone.insert.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.zone.list.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.dto.zone.update.UpdateGeometryZonesDTO;
import com.geotrack.apigeotrack.entities.GeometrySession;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import com.geotrack.apigeotrack.service.utils.GeometryForms;
import com.geotrack.apigeotrack.service.utils.GeometryValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeometryService {

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    // method to insert zone zones in database
    public void insertGeometryZones(GeometryZoneRequestDTO geometryZoneRequestDTO) {

        String upperCaseName = geometryZoneRequestDTO.name().toUpperCase().trim();

        // verificar se o nome ja existe no banco, se ja existir, lança um erro
        if (geometryZoneRepository.existsByName(upperCaseName)) {
            throw new IllegalArgumentException("Nome já existe no banco de dados!");
        }

        if (geometryZoneRequestDTO.name().isEmpty()) {
            throw new IllegalArgumentException("Nome vazio!");
        }

        // verify if type exists in Enum
        GeometryValidator.validatesType(String.valueOf(geometryZoneRequestDTO.type()));

        // validate if long and lat are null and validate if are valid true
        GeometryValidator.validatesCoordinator(geometryZoneRequestDTO.center().longitude(),
                geometryZoneRequestDTO.center().latitude());

        // validate radius are null
        GeometryValidator.validatesRadius(geometryZoneRequestDTO.radius());

        Integer status = 1;

        // create a complete object to send and save in database
        GeometrySession geometrySession = new GeometrySession(
                null,
                upperCaseName,
                status,
                geometryZoneRequestDTO.type(),
                geometryZoneRequestDTO.center().longitude(),
                geometryZoneRequestDTO.center().latitude(),
                geometryZoneRequestDTO.radius());

        geometryZoneRepository.save(geometrySession);
    }

    public List<GeometryZoneResponseDTO> listAllGeometryZones() {
        // select in database
        List<GeometrySession> geometryZones = geometryZoneRepository.listSessions();

        // mapping select objects to return
        return geometryZones.stream().map(geometryZone -> new GeometryZoneResponseDTO(
                geometryZone.getIdSession(),
                geometryZone.getName(),
                geometryZone.getType(),
                new CenterCoordinatesDTO(
                        geometryZone.getLongitude(),
                        geometryZone.getLatitude()
                ),
                geometryZone.getRadius()
        )).collect(Collectors.toList());
    }

    // method to update status zone zones
    public void deleteZones(DeleteZoneDTO deleteZoneDTO) {

        Optional<GeometrySession> zoneDeleted = geometryZoneRepository.findById(deleteZoneDTO.id());

        if (zoneDeleted.isEmpty()) {
            throw new IllegalArgumentException("Esta zona não existe, verifique o número de identificação!");
        }

        zoneDeleted.get().setStatus(0);

        geometryZoneRepository.save(zoneDeleted.get());
    }

    // method to update zone zones
    public void editZones(UpdateGeometryZonesDTO updateGeometryZonesDTO) {

        Optional<GeometrySession> zoneEdited = geometryZoneRepository.findByIdSession(updateGeometryZonesDTO.id());

        // verify if zone exists
        if (zoneEdited.isEmpty()) {
            throw new EntityNotFoundException("Esta Zona não existe, verifique o número de identificação!");
        }

        // verify if name is empty
        if (updateGeometryZonesDTO.name().isEmpty()) {
            throw new IllegalArgumentException("Nome da Zona Geométrica vazio!");
        } else {
            zoneEdited.get().setName(updateGeometryZonesDTO.name().toUpperCase());
        }
        ;

        // verify if type exists in Enum
        if (updateGeometryZonesDTO.type() == null) {
            throw new IllegalArgumentException("Tipo de Zona Geométrica invalido!");

        } else {
            GeometryValidator.validatesType(updateGeometryZonesDTO.type());
            zoneEdited.get().setType(GeometryForms.valueOf(updateGeometryZonesDTO.type()));
        }

        // verify if long and lat are null and validate if are valid true
        if (updateGeometryZonesDTO.center().longitude() == null || updateGeometryZonesDTO.center().latitude() == null) {

            throw new IllegalArgumentException("Longitude ou Latitude inválidos!");

        } else {
            GeometryValidator.validatesCoordinator(updateGeometryZonesDTO.center().longitude(),
                    updateGeometryZonesDTO.center().latitude());

            zoneEdited.get().setLongitude(updateGeometryZonesDTO.center().longitude());
            zoneEdited.get().setLatitude(updateGeometryZonesDTO.center().latitude());
        }

        // validate radius are null
        if (updateGeometryZonesDTO.radius() == null) {
            throw new IllegalArgumentException("Raio Invalido!");
        } else {

            GeometryValidator.validatesRadius(updateGeometryZonesDTO.radius());
            zoneEdited.get().setRadius(updateGeometryZonesDTO.radius());
        }

        // save in database
        geometryZoneRepository.save(zoneEdited.get());
    }


}
