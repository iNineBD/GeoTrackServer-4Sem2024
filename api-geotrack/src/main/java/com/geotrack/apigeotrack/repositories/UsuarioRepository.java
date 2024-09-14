package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("select u from Usuario u order by u.nome asc ")
    Optional<Page<Usuario>> listUser(PageRequest page);
}
