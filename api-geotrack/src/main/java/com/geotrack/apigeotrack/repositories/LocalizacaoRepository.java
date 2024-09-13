package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {
}
