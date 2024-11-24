package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.dto.routes.find.RoutesOracleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = "WITH cte_localizacao AS (\n" +
            "    SELECT \n" +
            "        ID_LOCALIZACAO,\n" +
            "        latitude,\n" +
            "        longitude,\n" +
            "        DATA_HORA,\n" +
            "        DATA_REFERENCIA AS DATA_REF,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        LAG(DATA_HORA) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA) AS DATA_HORA_ANTERIOR,\n" +
            "        CASE \n" +
            "            WHEN DATA_HORA - LAG(DATA_HORA) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA) > INTERVAL '15' MINUTE \n" +
            "            THEN 1 \n" +
            "            ELSE 0 \n" +
            "        END AS NOVO_GRUPO\n" +
            "    FROM \n" +
            "        ito1.localizacao\n" +
            "    WHERE \n" +
            "        ID_DISPOSITIVO IN (:idsDev)\n" +
            "        AND DATA_REFERENCIA BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') \n" +
            "                          AND TO_DATE(:finalDate, 'YYYY-MM-DD')\n" +
            "),\n" +
            "cte_grupos AS (\n" +
            "    SELECT \n" +
            "        ID_LOCALIZACAO,\n" +
            "        latitude,\n" +
            "        longitude,\n" +
            "        DATA_HORA,\n" +
            "        DATA_REF,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        DATA_HORA_ANTERIOR,\n" +
            "        NOVO_GRUPO,\n" +
            "        SUM(NOVO_GRUPO) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA) AS grupo_localizacao\n" +
            "    FROM \n" +
            "        cte_localizacao\n" +
            "),\n" +
            "cte_contador AS (\n" +
            "    SELECT \n" +
            "        grupo_localizacao,\n" +
            "        COUNT(*) AS contador,\n" +
            "        MIN(DATA_HORA) AS start_time,\n" +
            "        MAX(DATA_HORA) AS end_time \n" +
            "    FROM \n" +
            "        cte_grupos\n" +
            "    GROUP BY \n" +
            "        grupo_localizacao\n" +
            "),\n" +
            "cte_medias AS (\n" +
            "    SELECT \n" +
            "        grupo_localizacao,\n" +
            "        AVG(latitude) AS avg_latitude,\n" +
            "        AVG(longitude) AS avg_longitude\n" +
            "    FROM \n" +
            "        cte_grupos\n" +
            "    GROUP BY \n" +
            "        grupo_localizacao\n" +
            "),\n" +
            "cte_filtrados AS (\n" +
            "    SELECT \n" +
            "        g.*,\n" +
            "        c.contador,\n" +
            "        c.start_time,\n" +
            "        c.end_time,\n" +
            "        m.avg_latitude,\n" +
            "        m.avg_longitude\n" +
            "    FROM \n" +
            "        cte_grupos g\n" +
            "    JOIN \n" +
            "        cte_contador c\n" +
            "    ON \n" +
            "        g.grupo_localizacao = c.grupo_localizacao\n" +
            "    JOIN\n" +
            "        cte_medias m\n" +
            "    ON\n" +
            "        g.grupo_localizacao = m.grupo_localizacao\n" +
            "    WHERE \n" +
            "        c.contador > 2\n" +
            ")\n" +
            "SELECT \n" +
            "    ID_DISPOSITIVO,\n" +
            "    avg_latitude,\n" +
            "    avg_longitude,\n" +
            "    contador,\n" +
            "    grupo_localizacao,\n" +
            "    longitude,\n" +
            "    latitude,\n" +
            "    start_time,\n" +
            "    end_time\n" +
            "FROM \n" +
            "    cte_filtrados\n" +
            "ORDER BY \n" +
            "    grupo_localizacao", nativeQuery = true)
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
            "        AND DATA_REFERENCIA >= :dateStart\n" +
            "        AND DATA_REFERENCIA <= :dateEnd\n" +
            "), dados_com_grupos AS (\n" +
            "    SELECT\n" +
            "        ID_LOCALIZACAO,\n" +
            "        LATITUDE,\n" +
            "        LONGITUDE,\n" +
            "        DATA_HORA,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        RN,\n" +
            "        CASE \n" +
            "            WHEN (DATA_HORA - LAG(DATA_HORA) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY DATA_HORA)) >= INTERVAL '15' MINUTE\n" +
            "            THEN NULL \n" +
            "            ELSE RN \n" +
            "        END AS GRUPO\n" +
            "    FROM\n" +
            "        dados_filtrados\n" +
            "), grupos_sequenciais AS (\n" +
            "    SELECT\n" +
            "        ID_LOCALIZACAO,\n" +
            "        LATITUDE,\n" +
            "        LONGITUDE,\n" +
            "        DATA_HORA,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        SUM(CASE WHEN GRUPO IS NULL THEN 1 ELSE 0 END) OVER (PARTITION BY ID_DISPOSITIVO ORDER BY RN) AS GRUPO_SEQ\n" +
            "    FROM\n" +
            "        dados_com_grupos\n" +
            "), coordenadas_filtradas AS (\n" +
            "    SELECT\n" +
            "        ID_LOCALIZACAO,\n" +
            "        LATITUDE,\n" +
            "        LONGITUDE,\n" +
            "        DATA_HORA,\n" +
            "        ID_DISPOSITIVO,\n" +
            "        GRUPO_SEQ,\n" +
            "        LAG(LATITUDE) OVER (PARTITION BY GRUPO_SEQ ORDER BY DATA_HORA) AS LAT_PREV,\n" +
            "        LAG(LONGITUDE) OVER (PARTITION BY GRUPO_SEQ ORDER BY DATA_HORA) AS LONG_PREV\n" +
            "    FROM\n" +
            "        grupos_sequenciais\n" +
            ")\n" +
            "SELECT\n" +
            "    ID_LOCALIZACAO,\n" +
            "    LATITUDE,\n" +
            "    LONGITUDE,\n" +
            "    DATA_HORA,\n" +
            "    GRUPO_SEQ\n" +
            "FROM\n" +
            "    coordenadas_filtradas\n" +
            "WHERE\n" +
            "    (LATITUDE != LAT_PREV OR LAT_PREV IS NULL)\n" +
            "    OR (LONGITUDE != LONG_PREV OR LONG_PREV IS NULL)\n" +
            "ORDER BY\n" +
            "    ID_DISPOSITIVO, DATA_HORA", nativeQuery = true)
    List<Object[]>  findRouteByIdDevAndDate(Long idDev, LocalDate dateStart, LocalDate dateEnd);

}

