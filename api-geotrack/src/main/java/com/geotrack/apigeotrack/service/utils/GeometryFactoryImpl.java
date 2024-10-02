package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.dto.geometry.GeometryRequestDTO;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Component;

@Component
public class GeometryFactoryImpl {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    public Geometry convertDtoToGeometry(GeometryRequestDTO geometryRequestDTODTO) {
        Coordinate[] coordinates = geometryRequestDTODTO.coordinates();

        return switch (geometryRequestDTODTO.geometryType()) {
            case "Point" -> geometryFactory.createPoint(coordinates[0]);
            case "LineString" -> geometryFactory.createLineString(coordinates);
            case "Polygon" -> geometryFactory.createPolygon(coordinates);
            default -> throw new IllegalArgumentException("Tipo de geometria não suportado: " + geometryRequestDTODTO.geometryType());
        };

    }

}
