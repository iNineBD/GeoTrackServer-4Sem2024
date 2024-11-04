package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestUser(@Schema(description = "Número da página para a paginação", example = "1")
                          @JsonAlias("page")
                          int page,

                          @Schema(description = "Quantidade de itens por página", example = "10")
                          @JsonAlias("qtdPage")
                          int qtdPage)  {
}
