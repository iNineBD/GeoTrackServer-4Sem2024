package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Devices;
import com.geotrack.apigeotrack.entities.User;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataUsersDTO(@JsonAlias("idUser") int idUser,
                           @JsonAlias("idDevice") Long idDevice,
                           @JsonAlias("userDevice") String userDevice) {

    public DataUsersDTO(User user){
        this(user.getIdUser(),user.getDevices().get(0).getIdDevices(), user.getName() + " - " +user.getDevices().get(0).getCode());
    }

}
