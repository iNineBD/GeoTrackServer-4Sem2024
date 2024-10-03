package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeometryCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeometryCoordinatesRepository extends JpaRepository<GeometryCoordinates, Integer> {
}
