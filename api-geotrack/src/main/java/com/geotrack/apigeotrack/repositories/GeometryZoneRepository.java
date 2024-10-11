package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeometrySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeometryZoneRepository extends JpaRepository<GeometrySession, Integer> {

    @Query("select d from GeometrySession d where d.status = 1")
    List<GeometrySession> listSessions();

    boolean existsByName(String name);

    Optional<GeometrySession> findByIdSession(Integer id);

    @Query("select count(d) > 0 from GeometrySession d where d.name = :name and d.idSession <> :id")
    boolean existName(String name, Integer id);
}
