package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeometryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeometryZoneRepository extends JpaRepository<GeometryZone, Integer> {
}
