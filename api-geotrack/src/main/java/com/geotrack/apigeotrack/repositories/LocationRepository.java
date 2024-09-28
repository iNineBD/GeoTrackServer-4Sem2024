package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.dto.stopoint.StopPointDBDTO;
import com.geotrack.apigeotrack.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE l.dateTime BETWEEN :startDate AND :finalDate AND l.devices.idDevices" +
            "= :id ORDER BY l.dateTime ASC")
    List<Location> findByDispositivoIdDispositivo(Long id, Timestamp startDate, Timestamp finalDate);

    @Query("select l from Location l where l.devices.idDevices = :idDev and (l.latitude, l.longitude) in" +
            "(select l.latitude,l.longitude from Location l where l.devices.idDevices = :idDev group by l.latitude,l.longitude having count (l) > 1)" +
            "and l.dateReferences between :startDate AND :finalDate order by l.latitude,l.longitude, l.dateTime asc")
    List<Location> listLocal (Long idDev, LocalDate startDate, LocalDate finalDate);


    @Query(value = "WITH grouped_data AS (SELECT TRUNC((EXTRACT(HOUR FROM l.data_hora) * 60 + EXTRACT(MINUTE FROM l.data_hora)) / 15) AS time_group," +
            "l.latitude,l.longitude,l.data_hora,COUNT(*) OVER (PARTITION BY TRUNC((EXTRACT(HOUR FROM l.data_hora) * 60 + EXTRACT(MINUTE FROM l.data_hora)) / 15)) AS count " +
            "FROM ito1.localizacao l WHERE l.id_dispositivo = ?1 AND l.data_referencia BETWEEN ?2 AND ?3)" +
            "SELECT time_group,AVG(latitude) AS avg_latitude, AVG(longitude) AS avg_longitude,COUNT(*) AS count," +
            "LISTAGG(latitude || ' | ' || longitude, '; ') WITHIN GROUP (ORDER BY latitude, longitude) AS lat_long_list FROM grouped_data " +
            "GROUP BY time_group ORDER BY time_group",nativeQuery = true)
    List<Object[]> listLocal2(Long idDev, LocalDate startDate, LocalDate finalDate);
}
