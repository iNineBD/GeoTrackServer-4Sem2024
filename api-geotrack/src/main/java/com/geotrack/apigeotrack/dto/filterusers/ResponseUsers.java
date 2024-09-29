package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseUsers(@JsonAlias("UsersList") List<DataUsersDTO> listUsers,
                            @JsonAlias("currentPage") int currentePage,
                            @JsonAlias("totalPages") int totalPages) {
}
