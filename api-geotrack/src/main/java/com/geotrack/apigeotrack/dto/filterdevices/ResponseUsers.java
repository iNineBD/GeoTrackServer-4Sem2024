package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Usuario;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseUsers(@JsonAlias("UsersList") List<DataUsers> listUsers) {
}
