package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.geometry.delete.DeleteZoneDTO;
import com.geotrack.apigeotrack.dto.geometry.insert.CenterCoordinatesDTO;
import com.geotrack.apigeotrack.dto.geometry.insert.GeometryZoneRequestDTO;
import com.geotrack.apigeotrack.dto.geometry.listAll.GeometryZoneResponseDTO;
import com.geotrack.apigeotrack.entities.GeometrySession;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import com.geotrack.apigeotrack.service.utils.CoordinatesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        geometrySession.setStatus(1);
        geometrySession.setType(geometryZoneRequestDTO.type());
        geometrySession.setLongitude(geometryZoneRequestDTO.center().longitude());
        geometrySession.setLatitude(geometryZoneRequestDTO.center().latitude());
        geometrySession.setRadius(geometryZoneRequestDTO.radius());

        geometryZoneRepository.save(geometrySession);
    }

    public List<GeometryZoneResponseDTO> getAll(){
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

    // method to edit status geometry zones
    public void deleteZones(DeleteZoneDTO deleteZoneDTO){

        Optional<GeometrySession> zoneDeleted = geometryZoneRepository.findById(deleteZoneDTO.id());

        if(zoneDeleted.isEmpty()){
            throw new IllegalArgumentException("Esta zona não existe, verifique o número de identificação!");
        }

        zoneDeleted.get().setStatus(0);

        geometryZoneRepository.save(zoneDeleted.get());
    }


}
