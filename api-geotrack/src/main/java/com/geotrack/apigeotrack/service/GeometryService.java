package com.geotrack.apigeotrack.service;

import com.geotrack.apigeotrack.dto.geometry.GeometryRequestDTO;
import com.geotrack.apigeotrack.entities.GeometrySession;
import com.geotrack.apigeotrack.repositories.GeometryRepository;
import com.geotrack.apigeotrack.service.utils.GeometryFactoryImpl;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeometryService {

    private final GeometryRepository geometryRepository;
    private final GeometryFactoryImpl geometryFactory;

    @Autowired
    public GeometryService(GeometryRepository geometryRepository,
                           GeometryFactoryImpl geometryFactory) {
        this.geometryRepository = geometryRepository;
        this.geometryFactory = geometryFactory;
    }

    @Transactional
    public GeometrySession insertGeometry(GeometryRequestDTO geometryRequestDTO){

        Geometry geometry = geometryFactory.convertDtoToGeometry(geometryRequestDTO);

        GeometrySession session = new GeometrySession();
        session.setName(geometryRequestDTO.name());
        session.setGeometry(geometry);

        return geometryRepository.save(session);
    }
}
