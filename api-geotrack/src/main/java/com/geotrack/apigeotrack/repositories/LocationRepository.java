package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Location;
import com.geotrack.apigeotrack.dto.routes.find.RoutesOracleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = "", nativeQuery = true)
    List<Object[]> findStopPointsByUsers(List<Long> idsDev, LocalDate startDate, LocalDate finalDate);

    @Query(value = "WITH cte_localizacao AS (\n" +
            "    SELECT \n" +
            "        ID_LOCALIZACAO,\n" +
            "        latitude,\n" +
            "        longitude,\n" +
            "        DATA_HORA,\n" +
            "        DATA_REFERENCIA AS DATA_REF,\n" +
            "        id_dispositivo,\n" +
            "        LAG(DATA_HORA) OVER (PARTITION BY id_dispositivo ORDER BY DATA_HORA) AS DATA_HORA_ANTERIOR,\n" +
            "        -- Marca 1 onde a diferença é maior que 15 minutos, caso contrário 0\n" +
            "        CASE \n" +
            "            WHEN DATA_HORA - LAG(DATA_HORA) OVER (PARTITION BY id_dispositivo ORDER BY DATA_HORA) > INTERVAL '15' MINUTE \n" +
            "            THEN 1 \n" +
            "            ELSE 0 \n" +
            "        END AS MARCA_GRUPO\n" +
            "    FROM \n" +
            "        ito1.localizacao\n" +
            "    WHERE \n" +
            "        id_dispositivo IN (:idDev)\n" +
            "        AND DATA_REFERENCIA BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') \n" +
            "                              AND TO_DATE(:finalDate, 'YYYY-MM-DD')\n" +
            "),\n" +
            "cte_grupos AS (\n" +
            "    SELECT \n" +
            "        ID_LOCALIZACAO,\n" +
            "        latitude,\n" +
            "        longitude,\n" +
            "        DATA_HORA,\n" +
            "        DATA_REF,\n" +
            "        id_dispositivo,\n" +
            "        DATA_HORA_ANTERIOR,\n" +
            "        MARCA_GRUPO,\n" +
            "        -- Incrementa o grupo toda vez que encontrar um 1 em MARCA_GRUPO\n" +
            "        SUM(MARCA_GRUPO) \n" +
            "        OVER (PARTITION BY id_dispositivo ORDER BY DATA_HORA ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) + 1 AS GRUPO_LOCALIZACAO\n" +
            "    FROM \n" +
            "        cte_localizacao\n" +
            "),\n" +
            "cte_contagem AS (\n" +
            "    SELECT \n" +
            "        GRUPO_LOCALIZACAO,\n" +
            "        id_dispositivo,\n" +
            "        COUNT(*) AS TOTAL_ITENS,\n" +
            "        AVG(latitude) AS MEDIA_LATITUDE,\n" +
            "        AVG(longitude) AS MEDIA_LONGITUDE\n" +
            "    FROM \n" +
            "        cte_grupos\n" +
            "    GROUP BY \n" +
            "        GRUPO_LOCALIZACAO, id_dispositivo\n" +
            ")\n" +
            "SELECT \n" +
            "    g.ID_LOCALIZACAO,\n" +
            "    g.latitude,\n" +
            "    g.longitude,\n" +
            "    g.DATA_HORA,\n" +
            "    g.id_dispositivo,\n" +
            "    g.grupo_localizacao,\n" +
            "    c.MEDIA_LATITUDE,\n" +
            "    c.MEDIA_LONGITUDE\n" +
            "FROM \n" +
            "    cte_grupos g\n" +
            "JOIN \n" +
            "    cte_contagem c\n" +
            "ON \n" +
            "    g.GRUPO_LOCALIZACAO = c.GRUPO_LOCALIZACAO\n" +
            "   AND g.id_dispositivo = c.id_dispositivo\n" +
            "   AND c.total_itens >= 15\n" +
            "ORDER BY \n" +
            "    g.id_dispositivo, g.DATA_HORA;\n" +
            "\n" +
            "\n" +
            "\n" +
            "SELECT \n" +
            "    id_dispositivo,\n" +
            "    avg_latitude,\n" +
            "    avg_longitude,\n" +
            "    contador,\n" +
            "    grupo_localizacao,\n" +
            "    latitude,\n" +
            "    longitude,\n" +
            "    data_hora\n" +
            "FROM \n" +
            "    cte_filtrados\n" +
            "    \n" +
            "WHERE \n" +
            "(end_time - start_time) > INTERVAL '15' MINUTE\n" +
            "ORDER BY \n" +
            "    grupo_localizacao, data_hora", nativeQuery = true)
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

