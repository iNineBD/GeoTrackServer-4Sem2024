package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseUsers(@Schema(description = "Lista de usuários disponíveis para a página atual")
                            @JsonAlias("UsersList")
                            List<DataUsersDTO> listUsers,

                            @Schema(description = "Página atual da paginação", example = "1")
                            @JsonAlias("currentPage")
                            int currentPage,

                            @Schema(description = "Total de páginas disponíveis", example = "5")
                            @JsonAlias("totalPages")
                            int totalPages) {
}
