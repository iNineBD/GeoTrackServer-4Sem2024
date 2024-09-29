package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Devices;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataDevicesDTO(@JsonAlias("id") Long idDevice,
                             @JsonAlias("code") String code) {

    public DataDevicesDTO(Devices devices){
        this(devices.getIdDevices(), devices.getCode());
    }
}
