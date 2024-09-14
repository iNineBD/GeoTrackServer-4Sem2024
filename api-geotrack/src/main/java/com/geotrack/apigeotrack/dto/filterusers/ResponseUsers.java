package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Page;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseUsers(@JsonAlias("UsersList") Page<DataUsersDTO> listUsers) {
}
