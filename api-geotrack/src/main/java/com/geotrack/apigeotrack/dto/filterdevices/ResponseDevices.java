package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDevices(@Schema(description = "Lista de dispositivos disponíveis para a página atual")
                              @JsonAlias("listDevices")
                              List<DataDevicesDTO> listDevices,

                              @Schema(description = "Página atual da paginação", example = "1")
                              @JsonAlias("currentPage")
                              int currentPage,

                              @Schema(description = "Total de páginas disponíveis", example = "10")
                              @JsonAlias("totalPages")
                              int totalPages) {
}
