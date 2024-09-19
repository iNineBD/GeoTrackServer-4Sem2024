package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import com.geotrack.apigeotrack.entities.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {

    @Query(value = "SELECT * FROM ito1.localizacao l WHERE l.data_hora BETWEEN ?2 AND ?3 AND l.fk_id_dispositivo" +
            "= ?1 ORDER BY l.data_hora ASC",nativeQuery = true)
    List<Localizacao> findByDispositivoIdDispositivo(Long id, Timestamp dataInicio, Timestamp dataFinal);

    @Query("select l from Localizacao l where l.dispositivo.idDispositivo = :idDev and (l.latitude, l.longitude) in" +
            "(select l.latitude,l.longitude from Localizacao l where l.dispositivo.idDispositivo = :idDev group by l.latitude,l.longitude having count (l) > 1)" +
            "and l.dataHora between :dataInicio AND :dataFinal order by l.latitude,l.longitude, l.dataHora asc")
    List<Localizacao> listLocal (Long idDev, Timestamp dataInicio, Timestamp dataFinal);
}
