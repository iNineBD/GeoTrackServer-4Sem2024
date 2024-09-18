package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {

    @Query("SELECT l FROM Localizacao l WHERE l.dataHora BETWEEN :dataInicio AND :dataFinal AND l.dispositivo" +
            ".idDispositivo = :id ORDER BY l.dataHora ASC")
    List<Localizacao> findByDispositivoIdDispositivo(Long id, Timestamp dataInicio, Timestamp dataFinal);

}
