package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Devices;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataDevicesDTO(@Schema(description = "Identificador único do dispositivo", example = "1")
                             @JsonAlias("id")
                             Long idDevice,
                             @Schema(description = "Código de identificação do dispositivo", example = "ABC12345")
                             @JsonAlias("code")
                             String code) {

    public DataDevicesDTO(Devices devices) {
        this(devices.getIdDevices(), devices.getCode());
    }
}
