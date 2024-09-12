package com.geotrack.apigeotrack.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "GeoTrack",
                version = "v1",
                description = "Projeto integrador de geolocalização de dispositivos.",
                contact = @Contact(
                        name = "iNine",
                        url = "https://github.com/iNineBD"
                )
        )
)

public class OpenAPIConfig {
}
