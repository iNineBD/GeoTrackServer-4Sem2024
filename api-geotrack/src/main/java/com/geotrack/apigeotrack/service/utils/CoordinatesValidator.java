package com.geotrack.apigeotrack.service.utils;

import java.math.BigDecimal;

public class CoordinatesValidator {

    public static void validatesCoordinator(BigDecimal longitude, BigDecimal latitude) {
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
        if(radius == null){
            throw new IllegalArgumentException("Valor do Raio vazio!");
        }
        if(radius.compareTo(BigDecimal.ZERO) == 0){
            throw new IllegalArgumentException("Valor do Raio não pode ser 0!");
        }
    }
}
