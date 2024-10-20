package com.geotrack.apigeotrack.service.utils;

import com.geotrack.apigeotrack.dto.utils.ValidateZonesDTO;
import com.geotrack.apigeotrack.repositories.GeometryZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GeometryValidator {

    @Autowired
    GeometryZoneRepository geometryZoneRepository;

    public static void validatesAll(ValidateZonesDTO validateZonesDTO){

        if(validateZonesDTO.name().trim().isEmpty()){
            throw new IllegalArgumentException("Nome da Zona Geométrica esta vazio!");
        }

        validatesCoordinates(validateZonesDTO.center().longitude(),validateZonesDTO.center().latitude());
        validatesRadius(validateZonesDTO.radius());
        validatesType(validateZonesDTO.type().toString());

    }

    public static void validatesCoordinates(BigDecimal longitude, BigDecimal latitude) {
        // verify if lat or long are null
        if (longitude == null || latitude == null) {
            throw new IllegalArgumentException("Longitude ou Latitude São nulos, verifique!");
        }
        // verify if lat or long are true values
        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new IllegalArgumentException("Longitude inválida: " + longitude + ". Deve estar " + "entre -180 e " +
                    "180 graus.");
        }
        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new IllegalArgumentException("Latitude inválida: " + latitude + ". Deve estar " + "entre -90 e 90 " +
                    "graus.");
        }
    }

    // verify if radius are null
    public static void validatesRadius(BigDecimal radius) {
        if (radius == null) {
            throw new IllegalArgumentException("Valor do Raio vazio!");
        }
        if (radius.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Valor do Raio não pode ser 0!");
        }
    }

    // verify if type exists in Enum
    public static void validatesType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo da Zona Geométrica vazio!");
        }
        try {
            GeometryForms.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo da Zona Geométrica inválido!");
        }
    }
}
