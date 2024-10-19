package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.utils.ValidateZonesDTO;
import com.geotrack.apigeotrack.dto.zone.deleteCircle.DeleteZoneDTO;
import com.geotrack.apigeotrack.dto.zone.getAllCircle.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.dto.zone.insertCircle.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.dto.zone.insertCircle.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.zone.updateCircle.UpdateGeometryZonesDTO;
import com.geotrack.apigeotrack.entities.GeometrySession;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import com.geotrack.apigeotrack.service.utils.GeometryValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeometryService {

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    // method to insertCircle zone zones in database
    @Transactional
    public void insertGeometryZones(GeometryZoneRequestDTO geometryZoneRequestDTO) {

        if (geometryZoneRepository.existsByNameAndStatus(geometryZoneRequestDTO.name().toUpperCase().trim(),1)) {
            throw new IllegalArgumentException("Nome já existe no banco de dados!");
        }

        ValidateZonesDTO objectValid = new ValidateZonesDTO(
                geometryZoneRequestDTO.name(),
                geometryZoneRequestDTO.type(),
                geometryZoneRequestDTO.center(),
                geometryZoneRequestDTO.radius()
        );

        GeometryValidator.validatesAll(objectValid);

        // create a complete object to send and save in database
        GeometrySession geometrySession = new GeometrySession(
                null,
                geometryZoneRequestDTO.name().toUpperCase().trim(),
                1,
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

    // method to updateCircle status zone zones
    public void deleteZones(DeleteZoneDTO deleteZoneDTO) {

        Optional<GeometrySession> zoneDeleted = geometryZoneRepository.findById(deleteZoneDTO.id());

        if (zoneDeleted.isEmpty()) {
            throw new IllegalArgumentException("Esta zona não existe, verifique o número de identificação!");
        }

        zoneDeleted.get().setStatus(0);

        geometryZoneRepository.save(zoneDeleted.get());
    }

    // method to updateCircle zone zones
    @Transactional
    public void editZones(UpdateGeometryZonesDTO updateGeometryZonesDTO) {

        Optional<GeometrySession> zoneEdited = geometryZoneRepository.findByIdSession(updateGeometryZonesDTO.id());

        // verify if zone exists
        if (zoneEdited.isEmpty()) {
            throw new EntityNotFoundException("Esta Zona não existe, verifique o número de identificação!");
        }

        // verify if exist this name in database execpt
        if (geometryZoneRepository.existName(updateGeometryZonesDTO.name().toUpperCase().trim(),updateGeometryZonesDTO.id())) {
            throw new IllegalArgumentException("Nome já existe no banco de dados!");
        }


        ValidateZonesDTO objectValid = new ValidateZonesDTO(
                updateGeometryZonesDTO.name(),
                updateGeometryZonesDTO.type(),
                updateGeometryZonesDTO.center(),
                updateGeometryZonesDTO.radius()
        );

        // validating all values with validator
        GeometryValidator.validatesAll(objectValid);

        // setting values to save in database
        zoneEdited.get().setName(updateGeometryZonesDTO.name().toUpperCase().trim());
        zoneEdited.get().setType(updateGeometryZonesDTO.type());
        zoneEdited.get().setLongitude(updateGeometryZonesDTO.center().longitude());
        zoneEdited.get().setLatitude(updateGeometryZonesDTO.center().latitude());
        zoneEdited.get().setRadius(updateGeometryZonesDTO.radius());

        // save in database
        geometryZoneRepository.save(zoneEdited.get());
    }

}
