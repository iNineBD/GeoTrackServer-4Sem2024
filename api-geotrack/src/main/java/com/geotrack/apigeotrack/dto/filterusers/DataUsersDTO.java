package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Usuario;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataUsersDTO(@JsonAlias("id") int id,
                           @JsonAlias("name") String name) {

    public DataUsersDTO(Usuario usuario){
        this(usuario.getIdUsuario(),usuario.getNome());
    }

}