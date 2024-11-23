package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocation, Integer> {

    @Modifying
    @Query(value = "INSERT INTO ito1.geo_localizacao ( \n" +
            "loc_geo, data_hora, data_referencia, id_dispositivo \n" +
            ") VALUES ( \n" +
            "    SDO_GEOMETRY(2001, 4326, SDO_POINT_TYPE(?, ?, NULL), NULL, NULL), \n" +
            "    ?, \n" +
            "    TRUNC(TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS.FF3')), \n" +
            "    ? \n" +
            ")\n", nativeQuery = true)
    void insertGeoLocation(BigDecimal longitude, BigDecimal latitude, LocalDateTime dataHora, LocalDate dataReferencia, Long idDispositivo);
}