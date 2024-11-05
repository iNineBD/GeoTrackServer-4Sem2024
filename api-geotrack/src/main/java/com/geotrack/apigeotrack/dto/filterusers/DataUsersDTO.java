package com.geotrack.apigeotrack.dto.filterusers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geotrack.apigeotrack.entities.Devices;
import com.geotrack.apigeotrack.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO que representa um usuário com informações sobre o dispositivo.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record DataUsersDTO(@Schema(description = "Identificador único do usuário", example = "123")
                           @JsonAlias("idUser")
                           int idUser,

                           @Schema(description = "Identificador único do dispositivo", example = "456")
                           @JsonAlias("idDevice")
                           Long idDevice,

                           @Schema(description = "Nome do usuário", example = "João Silva")
                           @JsonAlias("userName")
                           String userName,

                           @Schema(description = "Nome do dispositivo", example = "Card XYZ")
                           @JsonAlias("deviceName")
                           String deviceName) {

    public DataUsersDTO(User user){
        this(user.getIdUser(),user.getDevices().get(0).getIdDevices(), user.getName(), user.getDevices().get(0).getCode());
    }

}
