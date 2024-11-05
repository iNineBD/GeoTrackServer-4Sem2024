package com.geotrack.apigeotrack.dto.stopointsessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Schema(name = "StopPointSessionResponseDTO", description = "DTO para resposta de sessão de pontos de parada")
@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointSessionResponseDTO (@JsonAlias("stopPontsInSession")
                                           @Schema(description = "Lista de pontos de parada na sessão")
                                           List<LocalizacaoDTO> stopPoints) {
}
