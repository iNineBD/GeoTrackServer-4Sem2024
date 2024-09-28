package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE l.dateTime BETWEEN :startDate AND :finalDate AND l.devices.idDevices" +
            "= :id ORDER BY l.dateTime ASC")
    List<Location> findByDispositivoIdDispositivo(Long id, Timestamp startDate, Timestamp finalDate);

    @Query("select l from Location l where l.devices.idDevices = :idDev and (l.latitude, l.longitude) in" +
            "(select l.latitude,l.longitude from Location l where l.devices.idDevices = :idDev group by l.latitude,l.longitude having count (l) > 1)" +
            "and l.dateReferences between :startDate AND :finalDate order by l.latitude,l.longitude, l.dateTime asc")
    List<Location> listLocal (Long idDev, LocalDate startDate, LocalDate finalDate);


    @Query(value = "WITH grouped_data AS (\n" +
            "    SELECT \n" +
            "        FLOOR((CAST(l.data_hora AS DATE) - TO_DATE('1970-01-01', 'YYYY-MM-DD')) * 24 * 60 / 15) AS time_group,\n" +
            "        l.latitude,\n" +
            "        l.longitude,\n" +
            "        l.data_hora,\n" +
            "        COUNT(*) OVER (PARTITION BY FLOOR((CAST(l.data_hora AS DATE) - TO_DATE('1970-01-01', 'YYYY-MM-DD')) * 24 * 60 / 15)) AS count\n" +
            "    FROM ito1.localizacao l \n" +
            "    WHERE l.id_dispositivo = :idDev \n" +
            "      AND l.data_referencia >= :startDate AND l.data_referencia <= :finalDate\n" +
            ")\n" +
            "SELECT \n" +
            "    time_group,\n" +
            "    AVG(latitude) AS avg_latitude, \n" +
            "    AVG(longitude) AS avg_longitude,\n" +
            "    COUNT(*) AS contador,\n" +
            "    MIN(data_hora) AS start_time,\n" +
            "    MAX(data_hora) AS end_time,\n" +
            "    LISTAGG(latitude || ';' || longitude, '|') WITHIN GROUP (ORDER BY latitude, longitude) AS lat_long_list\n" +
            "FROM grouped_data\n" +
            "GROUP BY time_group\n" +
            "HAVING count(*) > 2\n" +
            "ORDER BY time_group", nativeQuery = true)
    List<Object[]> listLocal2(Long idDev, LocalDate startDate, LocalDate finalDate);
}
