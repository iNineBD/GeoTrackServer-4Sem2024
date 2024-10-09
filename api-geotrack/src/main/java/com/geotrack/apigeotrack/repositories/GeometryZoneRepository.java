package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeometrySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeometryZoneRepository extends JpaRepository<GeometrySession, Integer> {

    @Query("select d from GeometrySession d where d.status = 1")
    List<GeometrySession> listSessions();



}
