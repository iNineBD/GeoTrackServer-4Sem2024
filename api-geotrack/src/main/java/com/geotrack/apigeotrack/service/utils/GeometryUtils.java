package com.geotrack.apigeotrack.service.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import java.math.BigDecimal;

public class GeometryUtils {

    public static Point criarGeometry(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude e longitude não podem ser nulos");
        }

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326); // SRID 4326
        Coordinate coordinate = new Coordinate(longitude.doubleValue(), latitude.doubleValue());
        return geometryFactory.createPoint(coordinate);
    }

}
