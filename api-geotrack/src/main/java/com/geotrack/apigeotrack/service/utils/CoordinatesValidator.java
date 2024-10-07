package com.geotrack.apigeotrack.service.utils;

import java.math.BigDecimal;

public class CoordinatesValidator {

    public static void validatesCoordinator(BigDecimal longitude, BigDecimal latitude) {
        // verificar se long ou lati são nulos
        if (longitude == null || latitude == null) {
            throw new IllegalArgumentException("Longitude ou Latitude São nulos, verifique!");
        }
        // verifica se long ou lati são valores validos
        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new IllegalArgumentException("Longitude inválida: " + longitude + ". Deve estar " + "entre -180 e " +
                    "180 graus.");
        }
        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new IllegalArgumentException("Latitude inválida: " + latitude + ". Deve estar " + "entre -90 e 90 " +
                    "graus.");
        }
    }
}
