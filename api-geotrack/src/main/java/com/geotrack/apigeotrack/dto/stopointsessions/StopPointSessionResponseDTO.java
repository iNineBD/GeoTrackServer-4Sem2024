package com.geotrack.apigeotrack.dto.stopointsessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.dto.stopoint.LocalizacaoDTO;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StopPointSessionResponseDTO (@JsonAlias("stopPontsInSession") List<LocalizacaoDTO> stopPoints) {
}
