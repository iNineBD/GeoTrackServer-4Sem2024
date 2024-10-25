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
            "    grouped_data.id_dispositivo,time_group", nativeQuery = true)
    List<Object[]> findStopPointsByUsers(List<Long> idsDev, LocalDate startDate, LocalDate finalDate);

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
    List<Object[]> findStopPointsByUsersAndSession(Long idDev, LocalDate startDate, LocalDate finalDate);


    @Query(value = "WITH dados_filtrados AS (\n" +
            "    SELECT\n" +
            "        ID_LOCALIZACAO,\n" +
            "        LATITUDE,\n" +
            "        LONGITUDE,\n" +
            "        DATA_HORA,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA) AS RN\n" +
            "    FROM\n" +
            "        ito1.localizacao\n" +
            "    WHERE\n" +
            "        ID_DISPOSITIVO = :idDev\n" +
            "        AND DATA_REFERENCIA = :dateToFind\n" +
            "        AND LATITUDE IS NOT NULL\n" +
            "        AND LONGITUDE IS NOT NULL\n" +
            "),\n" +
            "dados_com_grupos AS (\n" +
            "    SELECT\n" +
            "        ID_LOCALIZACAO,\n" +
            "        LATITUDE,\n" +
            "        LONGITUDE,\n" +
            "        DATA_HORA,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        RN,\n" +
            "        CASE \n" +
            "            WHEN (DATA_HORA - LAG(DATA_HORA) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA)) <= INTERVAL '2' MINUTE\n" +
            "                AND (LATITUDE != LAG(LATITUDE) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA) \n" +
            "                OR LONGITUDE != LAG(LONGITUDE) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA))\n" +
            "            THEN NULL \n" +
            "            ELSE RN \n" +
            "        END AS GRUPO\n" +
            "    FROM\n" +
            "        dados_filtrados\n" +
            "),\n" +
            "grupos_sequenciais AS (\n" +
            "    SELECT\n" +
            "        ID_LOCALIZACAO,\n" +
            "        LATITUDE,\n" +
            "        LONGITUDE,\n" +
            "        DATA_HORA,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        -- A cada vez que encontramos um novo grupo, incrementamos\n" +
            "        SUM(CASE WHEN GRUPO IS NULL THEN 1 ELSE 0 END) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY RN) AS GRUPO_SEQ\n" +
            "    FROM\n" +
            "        dados_com_grupos\n" +
            ")\n" +
            "SELECT\n" +
            "    ID_DISPOSITIVO,\n" +
            "    LISTAGG(LATITUDE || ',' || LONGITUDE || '|' || TO_CHAR(DATA_HORA, 'DD-MON-YYYY HH:MI:SS AM'), '->') WITHIN GROUP (ORDER BY DATA_HORA) AS COORDENADAS,\n" +
            "    MIN(DATA_HORA) AS INICIO_ROTA,\n" +
            "    MAX(DATA_HORA) AS FIM_ROTA,\n" +
            "    COUNT(DISTINCT LATITUDE || ',' || LONGITUDE) AS QTD_COORDENADAS\n" +
            "FROM\n" +
            "    grupos_sequenciais\n" +
            "WHERE\n" +
            "    LATITUDE IS NOT NULL\n" +
            "    AND LONGITUDE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    ID_DISPOSITIVO, GRUPO_SEQ\n" +
            "HAVING\n" +
            "    COUNT(DISTINCT LATITUDE || ',' || LONGITUDE) > 2\n" +
            "ORDER BY\n" +
            "    INICIO_ROTA;", nativeQuery = true)

    List<Object[]> findRouteByIdDevAndDate(Long idDev, LocalDate dateToFind);

}

