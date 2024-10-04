package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = "WITH grouped_data AS (SELECT d.id_dispositivo from ito1.dispositivo d where d.id_dispositivo in (:idsDev))\n" +
            "SELECT\n" +
            "    grouped_data.id_dispositivo, " +
            "    AVG(latitude)  AS avg_latitude,\n" +
            "    AVG(longitude) AS avg_longitude,\n" +
            "    COUNT(*) AS contador,\n" +
            "    MIN(data_hora) AS start_time,\n" +
            "    MAX(data_hora) AS end_time,\n" +
            "    LISTAGG(latitude\n" +
            "            || ';'\n" +
            "            || longitude, '|') WITHIN GROUP(\n" +
            "    ORDER BY\n" +
            "        latitude, longitude\n" +
            "    ) AS lat_long_list,\n" +
            "    floor((CAST(l.data_hora AS DATE) - TO_DATE('1970-01-01', 'YYYY-MM-DD')) * 24 * 60 / 15) AS time_group,\n" +
            "        COUNT(*) OVER(PARTITION BY floor((CAST(l.data_hora AS DATE) - TO_DATE('1970-01-01', 'YYYY-MM-DD')) * 24 * 60 / 15)) AS count \n" +
            "  FROM\n" +
            "    ito1.localizacao l, grouped_data \n" +
            "  WHERE\n" +
            "     l.id_dispositivo = grouped_data.id_dispositivo" +
            "        AND l.data_referencia >= :startDate \n" +
            "        AND l.data_referencia <= :finalDate \n" +
            "  GROUP BY\n" +
            "    floor((CAST(l.data_hora AS DATE) - TO_DATE('1970-01-01', 'YYYY-MM-DD')) * 24 * 60 / 15),grouped_data.id_dispositivo \n" +
            "HAVING\n" +
            "    COUNT(*) > 2 \n" +
            "  ORDER BY\n" +
            "    time_group,grouped_data.id_dispositivo", nativeQuery = true)
    List<Object[]> findLocalizationGroupedByDateWithInterval(List<Long> idsDev, LocalDate startDate, LocalDate finalDate);
}


