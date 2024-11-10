package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para requisição de dispositivos disponíveis")
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDevice(@Schema(description = "Identificador único do usuário", example = "123")
                            @JsonAlias("idUser")
                            int idUser,
                            @Schema(description = "Número da página para a paginação", example = "1")
                            @JsonAlias("pageNumber")
                            int page) {
}
