package com.geotrack.apigeotrack.repositories;

import com.geotrack.apigeotrack.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
