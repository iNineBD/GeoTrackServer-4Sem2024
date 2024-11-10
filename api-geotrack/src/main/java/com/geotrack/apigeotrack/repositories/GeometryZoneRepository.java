package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeometrySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeometryZoneRepository extends JpaRepository<GeometrySession, Integer> {

    @Query("select d from GeometrySession d where d.status = 1 order by NLSSORT(d.name, 'NLS_SORT = BINARY_AI') ASC")
    List<GeometrySession> listSessions();

    boolean existsByNameAndStatus(String name, Integer status);

    Optional<GeometrySession> findByIdSession(Integer id);

    @Query("select count(d) > 0 from GeometrySession d where d.name = :name and d.idSession <> :id and d.status = 1")
    boolean existName(String name, Integer id);

    @Query("select count(g) from GeometrySession g where g.status = 1")
    Integer qtdSessionsAssets();
}
