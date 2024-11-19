package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsertDataGeoRepository extends JpaRepository<GeoLocation, Integer> {
}
