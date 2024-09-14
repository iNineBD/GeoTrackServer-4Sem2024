package com.geotrack.apigeotrack.dto.filterdevices;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Dispositivo;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataDevices(@JsonAlias("id") int idDevice,
                          @JsonAlias("name") String name) {

    public DataDevices(Dispositivo dispositivo){
        this(dispositivo.getIdDispositivo(),dispositivo.getNome());
    }
}
