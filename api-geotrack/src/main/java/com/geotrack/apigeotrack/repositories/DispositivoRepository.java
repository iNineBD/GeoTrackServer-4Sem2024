package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {

    @Query("select d from Dispositivo d where d.usuario.idUsuario = :id order by d.nome asc")
    Optional<List<Dispositivo>> listDevices(int id);
}
