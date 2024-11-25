package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.dto.routes.find.RoutesOracleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = """
                WITH cte_localizacao AS (
                    SELECT 
                        ID_LOCALIZACAO,
                        latitude,
                        longitude,
                        DATA_HORA,
                        DATA_REFERENCIA AS DATA_REF,
                        id_dispositivo,
                        LAG(DATA_HORA) OVER (PARTITION BY id_dispositivo ORDER BY DATA_HORA) AS DATA_HORA_ANTERIOR,
                        CASE 
                            WHEN DATA_HORA - LAG(DATA_HORA) OVER (PARTITION BY id_dispositivo ORDER BY DATA_HORA) > INTERVAL '15' MINUTE 
                            THEN 1 
                            ELSE 0 
                        END AS NOVO_GRUPO
                    FROM 
                        ito1.localizacao
                    WHERE 
                        id_dispositivo IN :idsDev
                        AND DATA_REFERENCIA BETWEEN :startDate AND :finalDate
                ),
                cte_grupos AS (
                    SELECT 
                        ID_LOCALIZACAO,
                        latitude,
                        longitude,
                        DATA_HORA,
                        DATA_REF,
                        id_dispositivo,
                        DATA_HORA_ANTERIOR,
                        NOVO_GRUPO,
                        SUM(NOVO_GRUPO) OVER (PARTITION BY id_dispositivo ORDER BY DATA_HORA) AS grupo_localizacao
                    FROM 
                        cte_localizacao
                ),
                cte_contador AS (
                    SELECT 
                        grupo_localizacao,
                        COUNT(*) AS contador,
                        MIN(DATA_HORA) AS start_time,
                        MAX(DATA_HORA) AS end_time
                    FROM 
                        cte_grupos
                    GROUP BY 
                        grupo_localizacao
                ),
                cte_medias AS (
                    SELECT 
                        grupo_localizacao,
                        AVG(latitude) AS avg_latitude,
                        AVG(longitude) AS avg_longitude
                    FROM 
                        cte_grupos
                    GROUP BY 
                        grupo_localizacao
                ),
                cte_filtrados AS (
                    SELECT 
                        g.*,
                        c.contador,
                        c.start_time,
                        c.end_time,
                        m.avg_latitude,
                        m.avg_longitude
                    FROM 
                        cte_grupos g
                    JOIN 
                        cte_contador c
                    ON 
                        g.grupo_localizacao = c.grupo_localizacao
                    JOIN
                        cte_medias m
                    ON
                        g.grupo_localizacao = m.grupo_localizacao
                    WHERE 
                        c.contador > 2
                )
                SELECT 
                    id_dispositivo,
                    avg_latitude,
                    avg_longitude,
                    contador,
                    grupo_localizacao,
                    latitude,
                    longitude,
                    start_time,
                    end_time
                FROM 
                    cte_filtrados
                ORDER BY 
                    grupo_localizacao
            """, nativeQuery = true)
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
    List<Object[]> findRouteByIdDevAndDate(Long idDev, LocalDate dateStart, LocalDate dateEnd);

}

